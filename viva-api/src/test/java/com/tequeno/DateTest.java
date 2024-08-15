package com.tequeno;

import com.tequeno.utils.TimeUtil;
import org.junit.Test;

public class DateTest {

    @Test
    public void run() {

        String out = TimeUtil.now();
        System.out.println(out);

        String out1 = TimeUtil.nowDateNum();
        System.out.println(out1);
    }
}
