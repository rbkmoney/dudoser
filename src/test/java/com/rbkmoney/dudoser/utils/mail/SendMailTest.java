package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    MailSenderUtils mailSenderUtils;

    @Test
    public void testMe() throws MessagingException {
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("2222*****5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId("de3dddQscG135Hgf");
        paymentPayer.setDate("2016-03-22T00:12:00Z");
        paymentPayer.setTo("a.cherkasov@rbkmoney.com");

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPaid", paymentPayer);

        String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                paymentPayer.getInvoiceId(),
                paymentPayer.getDate(),
                paymentPayer.getAmountWithCurrency()
        );

        boolean result = mailSenderUtils.setFileNameTemplate("payment_paid.ftl")
                .setModel(model)
                .send("a.cherkasov@rbkmoney.com", "a.cherkasov@rbkmoney.com", subject);

        assertTrue(result);
    }

}
