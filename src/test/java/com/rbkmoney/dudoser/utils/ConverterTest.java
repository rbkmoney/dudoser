package com.rbkmoney.dudoser.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ConverterTest {

    @Test
    public void longToBigDecimal() throws Exception {
        assertEquals(new BigDecimal("0.00"), Converter.longToBigDecimal(0L));
        assertEquals(new BigDecimal("122.55"), Converter.longToBigDecimal(12255L));
        assertEquals(new BigDecimal("1225.54"), Converter.longToBigDecimal(122554L));
        assertEquals(new BigDecimal("-0.01"), Converter.longToBigDecimal(-1L));
    }

    @Test
    public void stringToLong() {
        assertEquals(new Long(0), Converter.stringToLong(""));
        assertEquals(new Long(12), Converter.stringToLong("12"));
        assertEquals(new Long(0), Converter.stringToLong("dfsf"));
        assertEquals(new Long(123), Converter.stringToLong("df123sf"));
        assertEquals(new Long(13), Converter.stringToLong("df<!13s?>./f"));
        assertEquals(new Long(0), Converter.stringToLong("df<!>./f"));
    }

}
