package com.rbkmoney.dudoser.model;

import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class PaymentPaidTest {

    PaymentPaid paymentPaid;

    @Before
    public void setUp() {
        paymentPaid = new PaymentPaid();
    }

    @Test
    public void testGetAmountWithCurrency() throws Exception {
        paymentPaid.setAmount(Converter.longToBigDecimal(12345L));
        paymentPaid.setCurrency("RUB");
        assertEquals("123.45 RUB", paymentPaid.getAmountWithCurrency());

        paymentPaid.setAmount(Converter.longToBigDecimal(12145345L));
        paymentPaid.setCurrency("USD");
        assertEquals("121453.45 USD", paymentPaid.getAmountWithCurrency());

        paymentPaid.setAmount(Converter.longToBigDecimal(0L));
        paymentPaid.setCurrency("BYR");
        assertEquals("0.00 BYR", paymentPaid.getAmountWithCurrency());

        paymentPaid.setAmount(Converter.longToBigDecimal(-1L));
        paymentPaid.setCurrency("FRA");
        assertEquals("-0.01 FRA", paymentPaid.getAmountWithCurrency());
    }

    @Test
    public void testSetAndGetDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        String dateTime = "2016-03-22T00:12:00Z";
        String expected = "2016-03-22";

        paymentPaid.setDate(dateTime);
        assertEquals(expected, paymentPaid.getDate());

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        assertEquals(expected, localDateTime.toLocalDate().toString());

        ZonedDateTime result = ZonedDateTime.parse(dateTime, formatter);
        assertEquals(expected, result.toLocalDate().toString());
    }

}
