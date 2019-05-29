package com.pattern.observer;

/**
 * 主题
 * 注册删除通知观察者
 */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}