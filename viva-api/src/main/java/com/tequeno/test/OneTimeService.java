package com.tequeno.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OneTimeService extends Remote {

    void sayHello(String name) throws RemoteException;
}
