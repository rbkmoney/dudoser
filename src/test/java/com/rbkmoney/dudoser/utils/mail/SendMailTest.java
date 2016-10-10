package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.dudoser.model.PaymentPaid;
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
        PaymentPaid paymentPaid = new PaymentPaid();
        paymentPaid.setCardType("visa");
        paymentPaid.setCardMaskPan("2222*****5555");
        paymentPaid.setCurrency("RUB");
        paymentPaid.setAmount(Converter.longToBigDecimal(111L));
        paymentPaid.setInvoiceId("de3dddQscG135Hgf");
        paymentPaid.setDate("2016-03-22T00:12:00Z");
        paymentPaid.setTo("a.cherkasov@rbkmoney.com");

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPaid", paymentPaid);

        String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                paymentPaid.getInvoiceId(),
                paymentPaid.getDate(),
                paymentPaid.getAmountWithCurrency()
        );

        boolean result = mailSenderUtils.setFileNameTemplate("payment_paid.ftl")
                .setModel(model)
                .send("a.cherkasov@rbkmoney.com", "a.cherkasov@rbkmoney.com", subject);

        assertTrue(result);
    }

}
