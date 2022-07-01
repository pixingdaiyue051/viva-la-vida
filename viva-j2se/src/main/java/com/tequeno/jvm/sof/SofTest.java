package com.tequeno.jvm.sof;

public class SofTest {

    public static void main(String[] args) {
        JvmStack test = new JvmStack();
        try {
            test.stackLengthSof();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("栈引用深度:" + test.stackLength);
        }
    }
}