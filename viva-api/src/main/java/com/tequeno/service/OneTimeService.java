package com.tequeno.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OneTimeService extends Remote {

    String sayHello(String name) throws RemoteException;
}
