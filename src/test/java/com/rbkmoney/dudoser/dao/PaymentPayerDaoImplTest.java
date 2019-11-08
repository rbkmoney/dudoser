package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.TestData;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.TemplateService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by inalarsanukaev on 06.12.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PaymentPayerDaoImplTest extends AbstractIntegrationTest{
    @Autowired
    PaymentPayerDao paymentPayerDao;
    @Autowired
    TemplateDao templateDao;
    @Autowired
    TemplateService templateService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        String invoiceId = "invId1";
        String paymentId = "paymId1";
        String partyId = "dsgsgr";
        String shopId = "1";
        Content invoiceMetadata = new Content("test", TestData.kebMetadata());
        //--------add invoice-------------
        paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru", invoiceMetadata);
        paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru", invoiceMetadata);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM dudos.payment_payer where type=CAST('INVOICE' AS dudos.payment_type)");
        Assert.assertEquals(1, list.size()); //invoice constraint

        //--------prepare payment info----
        PaymentPayer paymentPayer = paymentPayerDao.getInvoice(invoiceId).get();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-03-22T06:12:27Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");
        //------ add payment info------
        paymentPayerDao.addPayment(paymentPayer);
        paymentId = "paymId2";
        paymentPayer.setPaymentId(paymentId);
        paymentPayerDao.addPayment(paymentPayer);
        paymentPayerDao.addPayment(paymentPayer);

        list = jdbcTemplate.queryForList("SELECT * FROM dudos.payment_payer where type=CAST('PAYMENT' AS dudos.payment_type)");
        Assert.assertEquals(2, list.size()); //payment constraint

        //-------- get payment info-------
        PaymentPayer paymentPayerGet = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayerGet.getCardType(), "visa");
        //-------- check mail about payment ------
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "1").getBody();
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayerGet);
        model.put("formattedAmount", Converter.getFormattedAmount(paymentPayerGet.getAmount(), paymentPayerGet.getCurrency()));
        String filledFreeMarkerTemplateContent1 = templateService.getFilledContent(freeMarkerTemplateContent, model);
        assertTrue(filledFreeMarkerTemplateContent1.contains("Успешный платеж на сайте www.2ch.ru"));

        //-------- add refund ---------------
        PaymentPayer refundInfo = paymentPayerGet;
        refundInfo.setRefundAmount(Converter.longToBigDecimal(110L));
        refundInfo.setCurrency("RUB");
        String refundId = "234";
        refundInfo.setRefundId(refundId);
        refundInfo.setDate(TypeUtil.stringToLocalDateTime("2017-08-26T20:12:34.983390Z"));
        paymentPayerDao.addRefund(refundInfo);
        paymentPayerDao.addRefund(refundInfo);

        list = jdbcTemplate.queryForList("SELECT * FROM dudos.payment_payer where type=CAST('REFUND' AS dudos.payment_type)");
        Assert.assertEquals(1, list.size()); //refund constraint

        //----------- get refund ------------
        PaymentPayer refundInfoGet = paymentPayerDao.getRefund(invoiceId, paymentId, refundId).get();
        assertEquals(refundInfoGet.getRefundId(), refundId);
        //-------- check mail about refund ------
        String freeMarkerTemplateContentRefund = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED, "1", "1").getBody();
        Map<String, Object> modelRefund = new HashMap<>();
        modelRefund.put("paymentPayer", refundInfoGet);
        modelRefund.put("formattedAmount", Converter.getFormattedAmount(refundInfoGet.getRefundAmount(), refundInfoGet.getCurrency()));
        String filledFreeMarkerTemplateContent = templateService.getFilledContent(freeMarkerTemplateContentRefund, modelRefund);
        assertTrue(filledFreeMarkerTemplateContent.contains("Возврат средств"));
    }

    @Test
    public void testPaymentTerminalTool() throws Exception {

        String invoiceId = "invId2";
        String paymentId = "paymId2";
        String partyId = "dsgsgr4";
        String shopId = "1";
        //--------add invoice-------------
        paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.2ch.ru", null);
        //--------prepare payment info----
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId(invoiceId);
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-10-26T20:12:47.983390Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");
        //------ update payment info------
        paymentPayerDao.addPayment(paymentPayer);
        //-------- get payment info-------
        PaymentPayer paymentPayerGet = paymentPayerDao.getPayment(invoiceId, paymentId).get();
        assertEquals(paymentPayerGet.getToReceiver(), "i.ars@rbk.com");
        //-------- check mail about payment ------
        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "1").getBody();
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayerGet);
        model.put("formattedAmount", Converter.getFormattedAmount(paymentPayerGet.getAmount(), paymentPayerGet.getCurrency()));
        String filledFreeMarkerTemplateContent = templateService.getFilledContent(freeMarkerTemplateContent, model);
        assertFalse(filledFreeMarkerTemplateContent.contains("Номер карты"));
    }

    @Test
    public void paymentWithInvoiceDataTest() throws Exception {
        String invoiceId = "testInvoiceId";
        String paymentId = "testPaymentId";
        String partyId = "testPartyId";
        String shopId = "testShopId";
        String invoiceMetadata = "{\"invoiceTestKey\": \"testValue\"}";
        paymentPayerDao.addInvoice(invoiceId, partyId, shopId, "www.test.com",
                new Content("application/json", invoiceMetadata.getBytes(StandardCharsets.UTF_8)));
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(123L));
        paymentPayer.setInvoiceId(invoiceId);
        paymentPayer.setPaymentId(paymentId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-10-26T20:12:47.983390Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");

        String paymentMetadata = "{\"paymentTestKey\": \"testValue\"}";
        paymentPayer.setMetadata(new Content("application/json", paymentMetadata.getBytes(StandardCharsets.UTF_8)));

        paymentPayerDao.addPayment(paymentPayer);
        Optional<PaymentPayer> paymentWithInvoiceData = paymentPayerDao.getPaymentWithInvoiceData(invoiceId, paymentId);
        Assert.assertEquals(paymentMetadata, paymentWithInvoiceData.get().getMetadata().getDataValue());
        Assert.assertEquals(invoiceMetadata, paymentWithInvoiceData.get().getInvoiceMetadata().getDataValue());
    }
}
