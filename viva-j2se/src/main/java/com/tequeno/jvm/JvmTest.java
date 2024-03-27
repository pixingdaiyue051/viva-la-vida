package com.tequeno.jvm;

public class JvmTest {

    public static void main(String[] args) {

        DirectOomHandler handler1 = new DirectOomHandler();
        try {
            handler1.direct();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HeapOomHandler handler2 = new HeapOomHandler();
        handler2.obj();

        MethodOomHandler handler3 = new MethodOomHandler();
        handler3.constantPool();

        MethodOomHandler handler4 = new MethodOomHandler();
        handler4.dynamicGenKlass();


        JvmSofHandler test = new JvmSofHandler();
        try {
            test.stackLengthSof();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("栈引用深度:" + test.stackLength);
        }
    }

}