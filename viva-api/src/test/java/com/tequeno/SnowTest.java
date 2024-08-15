package com.tequeno;

import com.tequeno.utils.SnowFlakeUtil;
import org.junit.Test;

public class SnowTest {

    @Test
    public void run() {
        long nextId = SnowFlakeUtil.nextId();
        System.out.println(nextId);
        System.out.println(Long.MAX_VALUE);
    }
}
