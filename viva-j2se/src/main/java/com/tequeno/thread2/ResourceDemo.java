package com.tequeno.thread2;

public class ResourceDemo {
    public static void main(String[] args) {
        Resource r = new Resource();

        ResourceProducer rr = new ResourceProducer(r);
        ResourceConsumer rc = new ResourceConsumer(r);

        for (int i = 0; i < 3; i++) {
            new Thread(rr).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(rc).start();
        }
    }

}
