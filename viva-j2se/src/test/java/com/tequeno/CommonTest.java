package com.tequeno;

import com.tequeno.jdk8.time.TimeHandler;
import com.tequeno.net.InetConst;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CommonTest {

    @Test
    public void run() {
        System.out.println(64 * 1024);
        System.out.println(2 << 15);
        System.out.println(16 * 1024 * 1024);
        System.out.println(2 << 23);
        System.out.println("------WebKitFormBoundaryL7w09lyBRlbEwU2I--".getBytes(StandardCharsets.UTF_8).length);
    }

    @Test
    public void testTime() {
        TimeHandler handler = new TimeHandler();
//        handler.normal();
//        handler.diff();
        handler.timeMillis();
    }

    @Test
    public void testIp() {
        try {
            InetAddress i = InetAddress.getLocalHost();
            System.out.println(i.toString());

            InetAddress ia = InetAddress.getLoopbackAddress();
            System.out.println(ia.toString());

            InetAddress iaa = InetAddress.getByName("localhost");
            System.out.println(iaa.toString());

            InetAddress iaaa = InetAddress.getByName("www.baidu.com");
            System.out.println(iaaa.toString());

            InetAddress[] addrs = InetAddress.getAllByName("www.baidu.com");
            for (InetAddress inetAddress : addrs) {
                System.out.println(inetAddress.toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void testScanner() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String nextLine = scanner.nextLine();
                if (InetConst.BREAK_OUT.equals(nextLine)) {
                    System.out.println("结束");
                    break;
                }
                System.out.println("输入:" + nextLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
