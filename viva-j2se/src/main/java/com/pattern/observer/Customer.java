package com.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Observer {

    private List<MessageDataInfo> messageList;

    public Customer() {
        messageList = new ArrayList<>();
    }

    @Override
    public void update(MessageDataInfo message) {
        messageList.add(message);
    }

    public void display() {
        messageList.forEach(System.out::println);
    }
}