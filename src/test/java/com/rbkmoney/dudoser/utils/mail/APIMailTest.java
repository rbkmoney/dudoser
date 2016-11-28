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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Component
@Ignore("integration test")
public class APIMailTest {

    @Value("${server.port}")
    private String serverPort;

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
