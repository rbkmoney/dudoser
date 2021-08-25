package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.damsel.message_sender.MailBody;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageAttachment;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.testcontainers.annotations.postgresql.PostgresqlTestcontainerSingleton;
import com.rbkmoney.woody.api.event.ClientEventListener;
import com.rbkmoney.woody.api.event.CompositeClientEventListener;
import com.rbkmoney.woody.api.generator.IdGenerator;
import com.rbkmoney.woody.api.generator.TimestampIdGenerator;
import com.rbkmoney.woody.thrift.impl.http.THClientBuilder;
import com.rbkmoney.woody.thrift.impl.http.event.ClientEventLogListener;
import com.rbkmoney.woody.thrift.impl.http.event.HttpClientEventLogListener;
import org.apache.thrift.TException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@PostgresqlTestcontainerSingleton
public class ApiMailTest {

    @Value("${mail.port}")
    private int mailPort;
    @Value("${server.port}")
    private String serverPort;
    @Value("${mail.from}")
    private String from;
    @Value("${test.mail.to}")
    private String to;

    protected static <T> T createThriftRpcClient(Class<T> iface, IdGenerator idGenerator,
                                                 ClientEventListener eventListener, String url) {
        try {
            THClientBuilder clientBuilder = new THClientBuilder();
            clientBuilder.withAddress(new URI(url));
            clientBuilder.withIdGenerator(idGenerator);
            clientBuilder.withEventListener(eventListener);
            return clientBuilder.build(iface);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    Wiser wiser;

    @BeforeAll
    public void init() {
        wiser = new Wiser();
        wiser.setPort(mailPort);
        wiser.start();
    }

    @Test
    @Disabled
    public void testApi() throws TException, IOException, URISyntaxException, MessagingException {
        sendMail();
    }

    private void sendMail() throws TException, URISyntaxException, IOException, MessagingException {

        List<String> listTo = new ArrayList<String>();
        listTo.add(to);
        String mailBodyText = "Тело письма";
        MessageMail messageMail = new MessageMail(new MailBody(mailBodyText), from, listTo);
        messageMail.setSubject("Тема письма");
        Path filePath = Paths.get(getClass().getClassLoader().getResource("sadpepe.jpeg").toURI());
        byte[] messageBuf = Files.readAllBytes(
                filePath);
        List<MessageAttachment> attachments = new ArrayList<>();
        MessageAttachment e = new MessageAttachment();
        e.setName("sadpepe.png");
        e.setData(messageBuf);
        attachments.add(e);
        messageMail.setAttachments(attachments);
        Message m = new Message();
        m.setMessageMail(messageMail);
        ClientEventListener clientEventLogListener = new CompositeClientEventListener(
                new ClientEventLogListener(),
                new HttpClientEventLogListener()
        );
        MessageSenderSrv.Iface c =
                createThriftRpcClient(MessageSenderSrv.Iface.class, new TimestampIdGenerator(), clientEventLogListener,
                        "http://localhost:" + serverPort + "/dudos");
        c.send(m);

        for (WiserMessage message : wiser.getMessages()) {
            String envelopeSender = message.getEnvelopeSender();
            String envelopeReceiver = message.getEnvelopeReceiver();
            assertEquals(envelopeReceiver, to);
            assertEquals(envelopeSender, from);
        }
    }
}
