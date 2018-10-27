package com.tequeno.client;

import com.tequeno.test.OneTimeService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OneTimeClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            OneTimeService hello = (OneTimeService) registry.lookup("helloword");
            hello.sayHello("111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
