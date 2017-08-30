package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.mail.TemplateMailSenderUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by inalarsanukaev on 06.12.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentPayerDaoImplTest extends AbstractIntegrationTest{

    @Autowired
    PaymentPayerDao paymentPayerDao;
    @Autowired
    TemplateDao templateDao;
    @Autowired
    TemplateMailSenderUtils mailSenderUtils;
    @Test
    public void test() throws Exception {
        String invoiceId = "invId1";
        String paymentId = "paymId1";
        String partyId = "dsgsgr";
        String shopId = "1";
        assertTrue(paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru"));
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId(invoiceId);
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate("2016-10-26T20:12:47.983390Z");
        paymentPayer.setToReceiver("i.ars@rbk.com");
        assertTrue(paymentPayerDao.updatePayment(paymentPayer));
        PaymentPayer paymentPayer1 = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayer1.getCardType(), "visa");
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByTypeCode(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED);
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer1);
        mailSenderUtils.setModel(model);
        assertTrue(mailSenderUtils.getFilledFreeMarkerTemplateContent().contains("Успешный платеж на сайте www.2ch.ru"));
    }
}
