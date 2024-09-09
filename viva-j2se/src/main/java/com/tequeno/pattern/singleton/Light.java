package com.tequeno.pattern.singleton;

public class Light {

    private final long C = 299792458L;

    private long length;

    private long frequency;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public Light() {
        System.out.println("Light");
    }

    public static Light getInstance() {
        return LightHolder.INSTANCE;
    }

    private static class LightHolder {

        private static final Light INSTANCE;

        static {
            System.out.println("LightHolder static");
            INSTANCE = new Light();
        }
    }

}
