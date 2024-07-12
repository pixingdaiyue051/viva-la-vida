package com.tequeno;

import com.tequeno.thread.CachedPoolService;
import com.tequeno.thread.LockModel;
import com.tequeno.thread.ScheduledPoolService;
import com.tequeno.thread.StopThreadModel;
import org.junit.Test;

public class ThreadTest {

    @Test
    public void testPool() throws InterruptedException {
        CachedPoolService poolTest = new CachedPoolService();

//        System.out.println("test1");
//        poolTest.test1();

//        System.out.println("test2");
//        poolTest.test2();

//        System.out.println("test3");
//        poolTest.test3();

//        System.out.println("test4");
//        poolTest.test4();

        ScheduledPoolService poolService = new ScheduledPoolService();
        poolService.scheduleTick();
    }

    @Test
    public void testThread() {

//        Ticket ticket = new Ticket();
//        ticket.test();

//        SharedDataModel sharedDataModel = new SharedDataModel();
//        sharedDataModel.test();
//        sharedDataModel.test1();

//        DeadLockModel deadLockModel = new DeadLockModel();
//        deadLockModel.test();

        StopThreadModel st = new StopThreadModel();
//        st.sleepTest();
//        st.waitTest();
        st.signalTest();
//        st.joinTest();


//        LockModel lock = new LockModel();
//        lock.multiAct();
    }

}