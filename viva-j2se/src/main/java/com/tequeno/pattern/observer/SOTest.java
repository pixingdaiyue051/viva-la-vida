package com.tequeno.pattern.observer;

import java.time.LocalDateTime;

public class SOTest {
    public static void main(String[] args) {

        Customer c = new Customer();
        WDCPoster poster = new WDCPoster();
        poster.registerObserver(c);
        NYCTime time = new NYCTime();
        time.registerObserver(c);
        LDThemes themes = new LDThemes();
        themes.registerObserver(c);

        MessageDataInfo message1 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Poster");
        poster.messageChanged(message1);
        MessageDataInfo message2 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Time");
        time.messageChanged(message2);
        MessageDataInfo message3 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Theme");
        themes.messageChanged(message3);
        c.display();
    }
}
