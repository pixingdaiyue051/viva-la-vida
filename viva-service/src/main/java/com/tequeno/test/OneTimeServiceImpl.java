package com.tequeno.test;

import java.rmi.RemoteException;

public class OneTimeServiceImpl implements OneTimeService {

    @Override
    public void sayHello(String name) throws RemoteException {
        System.out.println("hello" + name);
    }
}
