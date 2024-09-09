package com.tequeno.async;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueEl implements Delayed {

    // 延迟截止时间（单面：毫秒）
    private final long delayTime;
    private final String name;

    public DelayedQueueEl(String name, long delayTime) {
        this.name = name;
        this.delayTime = (System.currentTimeMillis() + delayTime);
    }

    @Override
    // 获取剩余时间
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    // 队列里元素的排序依据
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    public long getDelayTime() {
        return delayTime;
    }

    public String getName() {
        return name;
    }
}
