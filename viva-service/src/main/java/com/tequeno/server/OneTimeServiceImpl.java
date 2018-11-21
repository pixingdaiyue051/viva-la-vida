package com.tequeno.server;

import com.tequeno.service.OneTimeService;

import java.rmi.RemoteException;

public class OneTimeServiceImpl implements OneTimeService {

    @Override
    public String sayHello(String name) throws RemoteException {
        return "hello" + name;
    }
}
