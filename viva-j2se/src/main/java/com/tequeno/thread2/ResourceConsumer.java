package com.tequeno.thread2;

public class ResourceConsumer implements Runnable {

    private Resource r;

    public ResourceConsumer(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (true) {
//			 r.out();
            r.outHolder();
        }
    }

}