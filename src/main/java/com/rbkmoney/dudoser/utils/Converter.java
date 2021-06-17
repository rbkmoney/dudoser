package com.rbkmoney.dudoser.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converter {

    public static BigDecimal longToBigDecimal(long amount) {
        return new BigDecimal(amount).movePointLeft(2);
    }

    public static String getFormattedAmount(BigDecimal amount, String currency) {
        return String.format(Locale.US, "%.2f %s", amount, currency);
    }
}
