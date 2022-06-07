package com.tequeno.classload;

public class TestClass {

    public class InnerClass {
        public void run() {
            System.out.println("InnerClass run");
        }
    }

    public static class InnerStaticClass {
        public void run() {
            System.out.println("InnerStaticClass run");
        }
    }

    public void run() {
        System.out.println("TestClass run");
    }
}