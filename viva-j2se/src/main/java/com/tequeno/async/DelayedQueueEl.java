package com.tequeno.async;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueEl implements Delayed {

    private final long delayTime;// 延迟截止时间(ms)
    private final LocalDateTime executeTime; // 任务将要执行的时间
    private final String name;

    public DelayedQueueEl(String name, long delayTime) {
        this.name = name;
        this.delayTime = System.currentTimeMillis() + delayTime;
        this.executeTime = LocalDateTime.now().plus(delayTime, ChronoUnit.MILLIS);
    }

    public String getName() {
        return name;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public LocalDateTime getExecuteTime() {
        return executeTime;
    }

    @Override
    // 获取剩余时间
    public long getDelay(TimeUnit unit) {
        return unit.convert(Duration.between(LocalDateTime.now(), executeTime));
    }

    @Override
    // 队列里元素的排序依据
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

}