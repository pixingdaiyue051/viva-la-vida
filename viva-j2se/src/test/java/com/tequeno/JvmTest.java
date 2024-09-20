package com.tequeno;

import com.tequeno.jvm.DirectOomHandler;
import com.tequeno.jvm.HeapOomHandler;
import com.tequeno.jvm.JvmSofHandler;
import com.tequeno.jvm.MethodOomHandler;
import org.junit.Test;

public class JvmTest {

    @Test
    public void testDirectOom() {

        DirectOomHandler handler = new DirectOomHandler();
        try {
            handler.run();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testHeapOom() {
        try {
            HeapOomHandler handler = new HeapOomHandler();
            handler.run();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testMethodOom() {
        MethodOomHandler handler = new MethodOomHandler();
        try {
            handler.constantPool();
//        handler.dynamicGenKlass();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testJvmSof() {
        JvmSofHandler test = new JvmSofHandler();
        try {
//            test.stackLengthSof();
            test.stackSizeSof();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("栈引用深度:" + test.stackLength);
        }
    }
}
