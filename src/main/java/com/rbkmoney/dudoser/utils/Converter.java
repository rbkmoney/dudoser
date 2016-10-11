package com.rbkmoney.dudoser.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Converter {

    public static BigDecimal longToBigDecimal(long amount) {
        DecimalFormat currencyFormat = new DecimalFormat();

        DecimalFormatSymbols dfs = currencyFormat.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        currencyFormat.setDecimalFormatSymbols(dfs);

        currencyFormat.setMinimumFractionDigits(2);
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setGroupingUsed(false);

        double roubles = (double) amount / 100;
        return new BigDecimal(currencyFormat.format(roubles));
    }

    public static Long stringToLong(String str) throws NumberFormatException {
        return (str.isEmpty()) ? null : Long.parseLong(str);
    }

}
