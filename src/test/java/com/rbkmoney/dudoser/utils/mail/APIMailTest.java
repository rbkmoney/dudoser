package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.damsel.message_sender.*;
import com.rbkmoney.dudoser.handler.DudoserHandler;
import com.rbkmoney.woody.api.event.ClientEventListener;
import com.rbkmoney.woody.api.event.CompositeClientEventListener;
import com.rbkmoney.woody.api.generator.IdGenerator;
import com.rbkmoney.woody.thrift.impl.http.THClientBuilder;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import com.rbkmoney.woody.thrift.impl.http.event.ClientEventLogListener;
import com.rbkmoney.woody.thrift.impl.http.event.HttpClientEventLogListener;
import com.rbkmoney.woody.thrift.impl.http.generator.TimestampIdGenerator;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.Servlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@Ignore("integration test")
public class APIMailTest {

    @Autowired
    MailSenderUtils mailSenderUtils;

    private HandlerCollection handlerCollection;
    protected Server server;
    @Value("${test.server.port:7778}")
    protected int serverPort;
    @Value("${mail.username}")
    private String from;
    @Value("${test.mail.to}")
    private String to;

    protected TProcessor tProcessor;

    @Before
    public void startJetty() throws Exception {

        server = new Server(serverPort);
        HandlerCollection contextHandlerCollection = new HandlerCollection(true); // important! use parameter
        // mutableWhenRunning==true
        this.handlerCollection = contextHandlerCollection;
        server.setHandler(contextHandlerCollection);

        server.start();
    }

    protected void addServlet(Servlet servlet, String mapping) {
        try {
            ServletContextHandler context = new ServletContextHandler();
            ServletHolder defaultServ = new ServletHolder(mapping, servlet);
            context.addServlet(defaultServ, mapping);
            handlerCollection.addHandler(context);
            context.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected String getUrlString() {
        return "http://localhost:" + serverPort;
    }

    protected String getUrlString(String contextPath) {
        return getUrlString() + contextPath;
    }


    protected static <T> T createThriftRPCClient(Class<T> iface, IdGenerator idGenerator, ClientEventListener eventListener, String url) {
        try {
            THClientBuilder clientBuilder = new THClientBuilder();
            clientBuilder.withAddress(new URI(url));
            clientBuilder.withHttpClient(HttpClientBuilder.create().build());
            clientBuilder.withIdGenerator(idGenerator);
            clientBuilder.withEventListener(eventListener);
            return clientBuilder.build(iface);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @Autowired
    DudoserHandler dudoserHandler;

    @Test
    public void testAPI() throws TException {
        ClientEventListener clientEventLogListener = new CompositeClientEventListener(
                new ClientEventLogListener(),
                new HttpClientEventLogListener()
        );
        addServlet(new THServiceBuilder().build(MessageSenderSrv.Iface.class, dudoserHandler), "/test");
        MessageSenderSrv.Iface c = createThriftRPCClient(MessageSenderSrv.Iface.class, new TimestampIdGenerator(), clientEventLogListener, getUrlString("/test"));
        List<String> listTo = new ArrayList<String>();
        listTo.add(to);
        Message m = new Message();
        MessageMail messageMail = new MessageMail(new MailBody("Privet"), from, listTo);
        messageMail.setSubject("Privet");
        List<MessageAttachment> attachments = new ArrayList<>();
        MessageAttachment e = new MessageAttachment("file_with_data.bin", ByteBuffer.wrap("ffffff".getBytes()));
        attachments.add(e);
        messageMail.setAttachments(attachments);
        m.setMessageMail(messageMail);
        c.send(m);
    }

    public static void main(String[] args) throws TException {
        ClientEventListener clientEventLogListener = new CompositeClientEventListener(
                new ClientEventLogListener(),
                new HttpClientEventLogListener()
        );
        MessageSenderSrv.Iface c = createThriftRPCClient(MessageSenderSrv.Iface.class, new TimestampIdGenerator(), clientEventLogListener, "http://localhost:8022/dudo");
        List<String> listTo = new ArrayList<String>();
        listTo.add("i.arsanukaev@rbkmoney.com");
        Message m = new Message();
        MessageMail messageMail = new MessageMail(new MailBody("Privet"), "i.arsanukaev@rbkmoney.com", listTo);
        messageMail.setSubject("Privet");
        List<MessageAttachment> attachments = new ArrayList<>();
        MessageAttachment e = new MessageAttachment("file_with_data.bin", ByteBuffer.wrap("ffffff".getBytes()));
        attachments.add(e);
        messageMail.setAttachments(attachments);
        m.setMessageMail(messageMail);
        c.send(m);
    }
}
