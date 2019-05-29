package com.pattern.observer;

/**
 * 观察者
 * 接受来自主体的更新
 */
public interface Observer {
    void update(MessageDataInfo message);
}
