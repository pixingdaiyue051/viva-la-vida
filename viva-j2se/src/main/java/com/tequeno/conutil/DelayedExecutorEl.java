package com.tequeno.conutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class DelayedExecutorEl implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(DelayedExecutorEl.class);
    private final String name;
    private final CountDownLatch count;

    public String getName() {
        return name;
    }

    public DelayedExecutorEl(String name, CountDownLatch count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public void run() {
        count.countDown();
        logger.info("{}执行任务{}", Thread.currentThread().getName(), name);
    }
}
