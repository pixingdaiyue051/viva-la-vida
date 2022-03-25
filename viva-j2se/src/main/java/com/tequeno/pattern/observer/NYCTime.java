package com.tequeno.pattern.observer;

public class NYCTime extends AbstractNewspaper {

    private MessageDataInfo message;

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        observerList.forEach(o -> o.update(message));
    }

    public void messageChanged(MessageDataInfo message) {
        message.setFrom(this);
        this.message = message;
        notifyObservers();
    }
}