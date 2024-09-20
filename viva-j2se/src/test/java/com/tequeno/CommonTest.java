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


    /**
     * String.intern
     * 如果字符串常量池有该字符串则返回常量池中该字符串引用
     * 如果没有则新建并加入常量池再返回常量池中该字符串引用
     */
    @Test
    public void testString() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
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
