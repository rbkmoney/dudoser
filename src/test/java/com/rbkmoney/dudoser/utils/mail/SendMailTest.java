package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.damsel.message_sender.MailBody;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.handler.DudoserHandler;
import com.rbkmoney.dudoser.utils.Converter;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.servlet.Servlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@Ignore("integration test")
public class SendMailTest {

    @Autowired
    TemplateMailSenderUtils mailSenderUtils;

    @Value("${mail.username}")
    private String from;
    @Value("${test.mail.to}")
    private String to;

    @Test
    public void testMe() throws MessagingException {

        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId("de3dddQscG135Hgf");
        paymentPayer.setDate("2016-10-26T20:12:47.983390Z");
        paymentPayer.setTo(to);

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer);

        String subject = String.format(MailSubject.FORMED_THROUGH.pattern,
                paymentPayer.getInvoiceId(),
                paymentPayer.getDate(),
                paymentPayer.getAmountWithCurrency()
        );

        String template = "payment_paid.ftl";

        boolean result = mailSenderUtils.setFileNameTemplate(template)
                .setModel(model)
                .send(from, to, subject);

        assertTrue(result);

        template = "create_invoice.ftl";

        result = mailSenderUtils.setFileNameTemplate(template)
                .setModel(model)
                .send(from, to, subject);

        assertTrue(result);
    }

}
