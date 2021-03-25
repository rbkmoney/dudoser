package com.rbkmoney.dudoser.utils;

import org.springframework.util.DigestUtils;

public class HashUtil {

    public static boolean checkHashMod(String str, int div, int mod) {
        return getMod(str, div) == mod;
    }

    public static int getMod(String str, int div) {
        return getIntHash(str) % div;
    }

    /**
     * @param str - input string for generate hash
     * @return int value of first 7 digits of md5-hash of invoice_id
     */
    public static int getIntHash(String str) {
        String hexStr = DigestUtils.md5DigestAsHex(str.getBytes());
        return Integer.parseInt(hexStr.substring(0, 7), 16);
    }
}
