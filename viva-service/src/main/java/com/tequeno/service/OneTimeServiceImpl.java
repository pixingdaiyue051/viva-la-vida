package com.tequeno.service;

import java.rmi.RemoteException;

public class OneTimeServiceImpl implements OneTimeService {

    @Override
    public String sayHello(String name) throws RemoteException {
        return "hello" + name;
    }
}
