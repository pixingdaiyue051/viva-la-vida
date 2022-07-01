package com.tequeno.thread;

public class ThreadTest {

    public static void main(String[] args) {

//        Ticket ticket = new Ticket();
//        ticket.test();

//        SharedDataModel sharedDataModel = new SharedDataModel();
//        sharedDataModel.test();
//        sharedDataModel.test1();

//        DeadLockModel deadLockModel = new DeadLockModel();
//        deadLockModel.test();

        StopThreadModel st = new StopThreadModel();
        st.signalTest();
//        st.waitTest();
//        st.joinTest();
//        st.sleepTest();
    }

}