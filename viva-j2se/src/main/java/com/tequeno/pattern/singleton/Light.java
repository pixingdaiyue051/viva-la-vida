package com.tequeno.pattern.singleton;

public class Light {

    public final static long C = 299792458L; // 光速常量

    public Light() {
        System.out.println("Light");
    }

    public double length(long frequency) {
        return C * 1.0 / frequency;
    }

    public double frequency(long length) {
        return C * 1.0 / length;
    }

}
