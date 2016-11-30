package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.damsel.message_sender.*;
import com.rbkmoney.woody.api.event.ClientEventListener;
import com.rbkmoney.woody.api.event.CompositeClientEventListener;
import com.rbkmoney.woody.api.generator.IdGenerator;
import com.rbkmoney.woody.thrift.impl.http.THClientBuilder;
import com.rbkmoney.woody.thrift.impl.http.event.ClientEventLogListener;
import com.rbkmoney.woody.thrift.impl.http.event.HttpClientEventLogListener;
import com.rbkmoney.woody.thrift.impl.http.generator.TimestampIdGenerator;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Component
//@Ignore("integration test")
public class APIMailTest {

    @Value("${server.port}")
    private String serverPort;
    @Value("${mail.from}")
    private String from;
    @Value("${test.mail.to}")
    private String to;

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

    @Test
    public void testAPI() throws TException {
        sendMail();
    }
/*
    public static void main(String[] args) throws TException {
        sendMail();
    }*/

    private void sendMail() throws TException {
        ClientEventListener clientEventLogListener = new CompositeClientEventListener(
                new ClientEventLogListener(),
                new HttpClientEventLogListener()
        );
        MessageSenderSrv.Iface c = createThriftRPCClient(MessageSenderSrv.Iface.class, new TimestampIdGenerator(), clientEventLogListener, "http://localhost:" + serverPort + "/dudos");
        List<String> listTo = new ArrayList<String>();
        listTo.add(to);
        Message m = new Message();
        MessageMail messageMail = new MessageMail(new MailBody("ЙОБА, ЭТО ТЫ?"), from, listTo);
        messageMail.setSubject("АЛЛО");
        FileInputStream fIn;
        FileChannel fChan;
        long fSize;
        ByteBuffer mBuf = null;

        try {
            fIn = new FileInputStream("C:\\users\\inal\\Downloads\\sadpepe.jpeg");
            fChan = fIn.getChannel();
            fSize = fChan.size();
            mBuf = ByteBuffer.allocate((int) fSize);
            fChan.read(mBuf);
            mBuf.rewind();
            fChan.close();
            fIn.close();
        } catch (IOException exc) {
            System.out.println(exc);
            System.exit(1);
        }
        List<MessageAttachment> attachments = new ArrayList<>();
        MessageAttachment e = new MessageAttachment("sadpepe.png", mBuf);
        attachments.add(e);
        messageMail.setAttachments(attachments);
        m.setMessageMail(messageMail);
        c.send(m);
    }
}
