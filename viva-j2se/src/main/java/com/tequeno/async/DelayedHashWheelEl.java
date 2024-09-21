package com.tequeno.async;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class DelayedHashWheelEl implements TimerTask {

    private final static Logger log = LoggerFactory.getLogger(DelayedHashWheelEl.class);
    private final String name;
    private final CountDownLatch count;

    public String getName() {
        return name;
    }

    public DelayedHashWheelEl(String name, CountDownLatch count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        count.countDown();
        log.info("{}执行任务{}", Thread.currentThread().getName(), name);
    }
}
