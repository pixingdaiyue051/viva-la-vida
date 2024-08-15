package com.tequeno.utils;

import com.tequeno.constants.HtPropertyConstant;

import java.util.Random;

public class HtCommonMethodUtil {

    private  static final Random random = new Random();

    public static String getRandomNumber(int len) {
        final String symbols = HtPropertyConstant.NUMBER_STR;
        final char[] nonceChars = new char[len];

        int first = random.nextInt(9) + 1;
        nonceChars[0] = symbols.charAt(first);

        for (int index = 1; index < len; ++index) {
            nonceChars[index] = symbols.charAt(random.nextInt(symbols.length()));
        }
        return new String(nonceChars);
    }

    public static String getDefaultOtp() {
        return getRandomNumber(HtPropertyConstant.OTP_LENGTH);
    }

    public static String getDefaultRandomStr() {
        return getRandomStr(HtPropertyConstant.OTP_LENGTH);
    }

    public static String getRandomStr(int len) {
        final String symbols = HtPropertyConstant.NUMBER_STR + HtPropertyConstant.LETTER_STR;
        final char[] nonceChars = new char[len];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = symbols.charAt(random.nextInt(symbols.length()));
        }
        return new String(nonceChars);
    }
}