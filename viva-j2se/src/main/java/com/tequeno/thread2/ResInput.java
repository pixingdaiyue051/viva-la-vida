package com.tequeno.thread2;

public class ResInput implements Runnable {

    private Res r;

    public ResInput(Res r) {
        this.r = r;
    }

    @Override
    public void run() {
        boolean b = true;
        while (true) {
            if (b) {
                r.set("Paul", "male");
                b = false;
            } else {
                r.set("Niya", "female");
                b = true;
            }
        }
    }

}

// // 方案2

class ResInput2 implements Runnable {

    private Res2 r;

    public ResInput2(Res2 r) {
        this.r = r;
    }

    @Override
    public void run() {
        boolean b = true;
        while (true) {
            synchronized (r) {
                if (r.flag) {
                    try {
                        r.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (b) {
                        r.name = "Paul";
                        r.sex = "male";
                        b = false;
                    } else {
                        r.name = "Niya";
                        r.sex = "female";
                        b = true;
                    }
                    r.flag = true;
                    r.notify();
                }
            }
        }
    }

}
