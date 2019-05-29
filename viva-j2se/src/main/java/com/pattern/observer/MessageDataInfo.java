package com.pattern.observer;

import java.time.LocalDateTime;

public class MessageDataInfo {

    private LocalDateTime time;

    private String message;

    private Subject from;

    public MessageDataInfo(LocalDateTime time, String message) {
        this.time = time;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Subject getFrom() {
        return from;
    }

    public void setFrom(Subject from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "MessageDataInfo{" +
                "time=" + time +
                ", message='" + message + '\'' +
                ", from=" + from +
                '}';
    }
}