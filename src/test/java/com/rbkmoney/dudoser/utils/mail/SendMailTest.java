package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Ignore("integration test")
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
