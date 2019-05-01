package com.tequeno.thread2;

public class ResourceProducer implements Runnable {

    private Resource r;

    public ResourceProducer(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (true) {
//			 r.set("Goods");
            r.setHolder("Goods");
        }
    }

}