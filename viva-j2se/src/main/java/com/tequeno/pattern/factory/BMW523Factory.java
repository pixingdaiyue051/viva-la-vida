package com.tequeno.pattern.factory;

public class BMW523Factory implements Factory {
    @Override
    public BMW523 createBMW() {
        return new BMW523();
    }
}