package com.tequeno.client;

import com.tequeno.service.OneTimeService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OneTimeClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            OneTimeService hello = (OneTimeService) registry.lookup("OneTimeService");
            String msg = hello.sayHello("111");
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
