package com.tequeno.pattern.observer;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractNewspaper implements Subject {
    protected List<Observer> observerList;

    public AbstractNewspaper() {
        this.observerList = new LinkedList<>();
    }
}