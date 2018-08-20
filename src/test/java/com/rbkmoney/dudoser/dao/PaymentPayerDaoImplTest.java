package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.mail.TemplateMailSenderUtils;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
        PaymentPayer paymentPayer = paymentPayerDao.getInvoice(invoiceId).get();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-10-26T20:12:47.983390Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");
        //------ add payment info------
        assertTrue(paymentPayerDao.addPayment(paymentPayer));
        paymentId = "paymId2";
        paymentPayer.setPaymentId(paymentId);
        assertTrue(paymentPayerDao.addPayment(paymentPayer));
        //-------- get payment info-------
        PaymentPayer paymentPayerGet = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayerGet.getCardType(), "visa");
        //-------- check mail about payment ------
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "1").getBody();
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayerGet);
        model.put("formattedAmount", Converter.getFormattedAmount(paymentPayerGet.getAmount(), paymentPayerGet.getCurrency()));
        mailSenderUtils.setModel(model);
        String filledFreeMarkerTemplateContent1 = mailSenderUtils.getFilledFreeMarkerTemplateContent();
        assertTrue(filledFreeMarkerTemplateContent1.contains("Успешный платеж на сайте www.2ch.ru"));

        //-------- add refund ---------------
        PaymentPayer refundInfo = paymentPayerGet;
        refundInfo.setRefundAmount(Converter.longToBigDecimal(110L));
        refundInfo.setCurrency("RUB");
        String refundId = "234";
        refundInfo.setRefundId(refundId);
        refundInfo.setDate(TypeUtil.stringToLocalDateTime("2017-08-26T20:12:34.983390Z"));
        paymentPayerDao.addRefund(refundInfo);
        //----------- get refund ------------
        PaymentPayer refundInfoGet = paymentPayerDao.getRefund(invoiceId, paymentId, refundId).get();
        assertEquals(refundInfoGet.getRefundId(), refundId);
        //-------- check mail about refund ------
        String freeMarkerTemplateContentRefund = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED, "1", "1").getBody();
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContentRefund);
        Map<String, Object> modelRefund = new HashMap<>();
        modelRefund.put("paymentPayer", refundInfoGet);
        modelRefund.put("formattedAmount", Converter.getFormattedAmount(refundInfoGet.getRefundAmount(), refundInfoGet.getCurrency()));
        mailSenderUtils.setModel(modelRefund);
        String filledFreeMarkerTemplateContent = mailSenderUtils.getFilledFreeMarkerTemplateContent();
        assertTrue(filledFreeMarkerTemplateContent.contains("Возврат средств"));
    }

    @Test
    public void testPaymentTerminalTool() throws Exception {

        String invoiceId = "invId2";
        String paymentId = "paymId2";
        String partyId = "dsgsgr4";
        String shopId = "1";
        //--------add invoice-------------
        assertTrue(paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru"));
        //--------prepare payment info----
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId(invoiceId);
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-10-26T20:12:47.983390Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");
        //------ update payment info------
        assertTrue(paymentPayerDao.addPayment(paymentPayer));
        //-------- get payment info-------
        PaymentPayer paymentPayerGet = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayerGet.getToReceiver(), "i.ars@rbk.com");
        //-------- check mail about payment ------
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "1").getBody();
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayerGet);
        model.put("formattedAmount", Converter.getFormattedAmount(paymentPayerGet.getAmount(), paymentPayerGet.getCurrency()));
        mailSenderUtils.setModel(model);
        String filledFreeMarkerTemplateContent = mailSenderUtils.getFilledFreeMarkerTemplateContent();
        assertFalse(filledFreeMarkerTemplateContent.contains("Номер карты"));
    }
}
