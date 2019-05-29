package com.pattern.factory;

public class BMW320Factory implements Factory {
    @Override
    public BMW320 createBMW() {
        return new BMW320();
    }
}