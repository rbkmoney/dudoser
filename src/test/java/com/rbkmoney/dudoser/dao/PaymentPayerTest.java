package com.rbkmoney.dudoser.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.dudoser.TestData;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.template.model.KebMetadata;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PaymentPayerTest {

    PaymentPayer paymentPayer;

    @Before
    public void setUp() {
        paymentPayer = new PaymentPayer();
    }

    @Test
    public void testGetAmountWithCurrency() throws Exception {
        paymentPayer.setAmount(Converter.longToBigDecimal(12345L));
        paymentPayer.setCurrency("RUB");
        assertEquals("123.45 RUB", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(12145345L));
        paymentPayer.setCurrency("USD");
        assertEquals("121453.45 USD", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(0L));
        paymentPayer.setCurrency("BYR");
        assertEquals("0.00 BYR", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(-1L));
        paymentPayer.setCurrency("FRA");
        assertEquals("-0.01 FRA", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
    }

    @Test
    public void testKebParseMetadata() throws IOException {
        Content content = new Content();
        content.setType("test");
        content.setData(TestData.kebMetadata());
        KebMetadata kebMetadata = new ObjectMapper().readValue(content.getDataValue(), KebMetadata.class);
        assertEquals(kebMetadata.getTxnId(), "12345678901234567890");
        assertEquals((int) kebMetadata.getCheckResultCode(), 1);
        assertEquals(kebMetadata.getCustomerInitials(), "Говнов Петр Сергеевич");
        assertEquals(kebMetadata.getCommission(), true);
        assertEquals(kebMetadata.getAccount(), "40817810059900010245");
        assertEquals(kebMetadata.getSum().toString(), "10000.00");
        assertEquals(kebMetadata.getCommissionAmount().toString(), "0.01");
    }
}
