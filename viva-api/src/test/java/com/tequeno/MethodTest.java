package com.tequeno;

import com.tequeno.utils.HtCommonMethodUtil;
import com.tequeno.utils.EncoderUtil;
import org.junit.Test;

public class MethodTest {

    @Test
    public void run() {

        String password = HtCommonMethodUtil.getDefaultOtp();
        System.out.println(password);
        String salt = HtCommonMethodUtil.getDefaultRandomStr();
        System.out.println(salt);

        String out = EncoderUtil.md5Encode(password, salt);
        System.out.println(out);

        String out1 = EncoderUtil.sha256Encode(password, salt);
        System.out.println(out1);
    }
}
