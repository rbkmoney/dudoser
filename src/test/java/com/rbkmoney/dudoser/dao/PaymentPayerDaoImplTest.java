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
        //--------add invoice-------------
        assertTrue(paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru"));
        //--------prepare payment info----
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId(invoiceId);
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate("2016-10-26T20:12:47.983390Z");
        paymentPayer.setToReceiver("i.ars@rbk.com");
        //------ update payment info------
        assertTrue(paymentPayerDao.updatePayment(paymentPayer));
        //-------- get payment info-------
        PaymentPayer paymentPayerGet = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayerGet.getCardType(), "visa");
        //-------- check mail about payment ------
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByTypeCode(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED);
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayerGet);
        mailSenderUtils.setModel(model);
        assertTrue(mailSenderUtils.getFilledFreeMarkerTemplateContent().contains("Успешный платеж на сайте www.2ch.ru"));

        //-------- add refund ---------------
        PaymentPayer refundInfo = paymentPayerGet;
        refundInfo.setAmount(Converter.longToBigDecimal(112L));
        refundInfo.setCurrency("RUB");
        String refundId = "234";
        refundInfo.setRefundId(refundId);
        refundInfo.setDate("2017-08-26T20:12:34.983390Z");
        paymentPayerDao.addRefund(refundInfo);
        //----------- get refund ------------
        PaymentPayer refundInfoGet = paymentPayerDao.getRefund(invoiceId, paymentId, refundId).get();
        assertEquals(refundInfoGet.getRefundId(), refundId);
        //-------- check mail about refund ------
        String freeMarkerTemplateContentRefund = templateDao.getTemplateBodyByTypeCode(EventTypeCode.PAYMENT_STATUS_CHANGED_REFUNDED);
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContentRefund);
        Map<String, Object> modelRefund = new HashMap<>();
        modelRefund.put("paymentPayer", refundInfoGet);
        mailSenderUtils.setModel(modelRefund);
        assertTrue(mailSenderUtils.getFilledFreeMarkerTemplateContent().contains("Возврат средств на сайте www.2ch.ru"));
    }
}
