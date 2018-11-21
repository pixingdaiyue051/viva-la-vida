package com.tequeno.server;

import com.tequeno.service.OneTimeService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class OneTimeServer {

    public static void main(String[] args) {
        try {
            OneTimeService hello = new OneTimeServiceImpl();
            OneTimeService stub = (OneTimeService) UnicastRemoteObject.exportObject(hello, 9999);
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("OneTimeService", stub);
            System.out.println("绑定成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
