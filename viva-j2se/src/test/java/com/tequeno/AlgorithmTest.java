package com.tequeno;

import com.tequeno.algorithm.EvaluateHandler;
import com.tequeno.algorithm.IdHandler;
import com.tequeno.jdk8.time.TimeHandler;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class AlgorithmTest {

    @Test
    public void testMathRadio() {
        EvaluateHandler handler = new EvaluateHandler();

        for (int i = 0; i < 1000; i++) {
            System.out.println(handler.sumReciprocal(i + 1));
        }
        System.out.println(handler.sumEqualDifference(100));
        System.out.println(handler.sumEqualRatio(10));
        System.out.println(handler.sumFibonacci(10));
    }

    /**
     * String.intern
     * 如果字符串常量池有该字符串则返回常量池中该字符串引用
     * 如果没有则新建并加入常量池再返回常量池中该字符串引用
     */
    @Test
    public void testStringQuery() {
        EvaluateHandler handler = new EvaluateHandler();

        String str = "robot,*obot,r**ot,r***t,r****,robot,*obot,r**ot,r***t,r****";
        String evaluateStr = handler.queryStrHandler(str);
        System.out.println(evaluateStr);
        String evaluateBuf = handler.queryStrHandlerBuf(str);
        System.out.println(evaluateBuf);
        String max = handler.getMaxLengthSubString("7nvi84***55/////dfkb89");
        System.out.println(max);

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    @Test
    public void testExchange() {
        EvaluateHandler handler = new EvaluateHandler();

        int sec = 0;
        System.out.println(handler.exchangeSec(sec));
        sec = 59;
        System.out.println(handler.exchangeSec(sec));
        sec = 60;
        System.out.println(handler.exchangeSec(sec));
        sec = 61;
        System.out.println(handler.exchangeSec(sec));
        sec = 3599;
        System.out.println(handler.exchangeSec(sec));
        sec = 3600;
        System.out.println(handler.exchangeSec(sec));
        sec = 3601;
        System.out.println(handler.exchangeSec(sec));
        sec = 86399;
        System.out.println(handler.exchangeSec(sec));
        sec = 86400;
        System.out.println(handler.exchangeSec(sec));
        sec = 86401;
        System.out.println(handler.exchangeSec(sec));
    }

    @Test
    public void time() {
        TimeHandler handler = new TimeHandler();
//        handler.normal();
//        handler.diff();
        handler.timeMillis();
    }

    @Test
    public void testPinyin() {
        EvaluateHandler handler = new EvaluateHandler();

        String chinese = "中文转拼音";
        String pinyin = handler.to1stPinyin(chinese);
        System.out.println(pinyin);
    }

    @Test
    public void testId() {

        String idCard = "632323190605261343";
        idCard = "510921197912177590";
        idCard = "510704198205292714";
        boolean check = IdHandler.check(idCard);
        System.out.println(check);

        String idCard1 = IdHandler.singleOne();
        System.out.println(idCard1);
        boolean check1 = IdHandler.check(idCard);
        System.out.println(check1);

        List<String> randomList = IdHandler.randomList(10);
        randomList.forEach(System.out::println);
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

}