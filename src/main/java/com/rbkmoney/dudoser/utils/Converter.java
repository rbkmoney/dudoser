package com.rbkmoney.dudoser.utils;

import java.math.BigDecimal;

public class Converter {

    public static BigDecimal longToBigDecimal(long amount) {
        return new BigDecimal(amount).divide(BigDecimal.valueOf(100)).setScale(2);
    }
}
