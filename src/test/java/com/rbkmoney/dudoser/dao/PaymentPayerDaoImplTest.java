package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.TestData;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.TemplateService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by inalarsanukaev on 06.12.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PaymentPayerDaoImplTest extends AbstractIntegrationTest {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void test() {
        //--------prepare payment info----
        PaymentPayer paymentPayer = PaymentPayer.builder().build();
        paymentPayer.setInvoiceId("invId1");
        paymentPayer.setPaymentId("paymId1");
        paymentPayer.setPartyId("dsgsgr");
        paymentPayer.setShopId("1");
        paymentPayer.setMetadata(new Content("test", TestData.kebMetadata()));
        paymentPayer.setShopUrl("www.2ch.ru");
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-03-22T06:12:27Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");

        addPayment(paymentPayer);

        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "1").getBody();
        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer);
        model.put("formattedAmount", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
        String filledFreeMarkerTemplateContent1 = templateService.getFilledContent(freeMarkerTemplateContent, model);
        assertTrue(filledFreeMarkerTemplateContent1.contains("Успешный платеж на сайте www.2ch.ru"));

        //-------- add refund ---------------
        paymentPayer.setRefundAmount(Converter.longToBigDecimal(110L));
        paymentPayer.setCurrency("RUB");
        String refundId = "234";
        paymentPayer.setRefundId(refundId);
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2017-08-26T20:12:34.983390Z"));

        addRefund(paymentPayer);

        //-------- check mail about refund ------
        String freeMarkerTemplateContentRefund = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED, "1", "1").getBody();
        Map<String, Object> modelRefund = new HashMap<>();
        modelRefund.put("paymentPayer", paymentPayer);
        modelRefund.put("formattedAmount", Converter.getFormattedAmount(paymentPayer.getRefundAmount(), paymentPayer.getCurrency()));
        String filledFreeMarkerTemplateContent = templateService.getFilledContent(freeMarkerTemplateContentRefund, modelRefund);
        assertTrue(filledFreeMarkerTemplateContent.contains("Возврат средств"));
    }

    private void addPayment(PaymentPayer payment) {
        String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, amount, currency, card_type, card_mask_pan, date, to_receiver, type, content_type, content_data) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type), :content_type, :content_data) " +
                "ON CONFLICT (invoice_id, payment_id) WHERE payment_id is not null and refund_id is null DO NOTHING";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", payment.getInvoiceId())
                .addValue("party_id", payment.getPartyId())
                .addValue("shop_id", payment.getShopId())
                .addValue("shop_url", payment.getShopUrl())
                .addValue("payment_id", payment.getPaymentId())
                .addValue("amount", payment.getAmount())
                .addValue("currency", payment.getCurrency())
                .addValue("card_type", payment.getCardType())
                .addValue("card_mask_pan", payment.getCardMaskPan())
                .addValue("date", payment.getDate())
                .addValue("to_receiver", payment.getToReceiver())
                .addValue("type", "PAYMENT")
                .addValue("content_type", payment.getMetadata() != null ? payment.getMetadata().getType() : null)
                .addValue("content_data", payment.getMetadata() != null ? payment.getMetadata().getData() : null);

        namedParameterJdbcTemplate.update(sql, params);
    }

    private void addRefund(PaymentPayer refund) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, refund_id, amount, refund_amount, currency, card_type, card_mask_pan, date, to_receiver, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :refund_id, :amount, :refund_amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type)) " +
                "ON CONFLICT (invoice_id, payment_id, refund_id) WHERE payment_id is not null and refund_id is not null DO NOTHING";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", refund.getInvoiceId())
                .addValue("party_id", refund.getPartyId())
                .addValue("shop_id", refund.getShopId())
                .addValue("shop_url", refund.getShopUrl())
                .addValue("payment_id", refund.getPaymentId())
                .addValue("refund_id", refund.getRefundId())
                .addValue("amount", refund.getAmount())
                .addValue("refund_amount", refund.getRefundAmount())
                .addValue("currency", refund.getCurrency())
                .addValue("card_type", refund.getCardType())
                .addValue("card_mask_pan", refund.getCardMaskPan())
                .addValue("date", refund.getDate())
                .addValue("to_receiver", refund.getToReceiver())
                .addValue("type", "REFUND");

        namedParameterJdbcTemplate.update(sql, params);
    }
}
