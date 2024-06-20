package com.tequeno;

import com.tequeno.algorithm.*;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class AlgorithmTest {

    @Test
    public void testDecent() {

        Decent de = new Decent();
        for (int i = 0; i < 1000; i++) {
            System.out.println(de.sumReciprocal(i + 1));
        }
        System.out.println(de.sumEqualDifference(100));
        System.out.println(de.sumEqualRatio(10));
        de.sumFibonacci(10);
    }

    @Test
    public void testEval() {

        EvaluateHandler handler = new EvaluateHandler();
//        String str = "robot,*obot,r**ot,r***t,r****,robot,*obot,r**ot,r***t,r****";
//
//        long l1 = System.currentTimeMillis();
//        String evaluateStr = handler.evaluate(str);
//        System.out.println(evaluateStr);
//        long l2 = System.currentTimeMillis();
//        System.out.println(l2 - l1);
//
//        long l3 = System.currentTimeMillis();
//        String evaluateBuf = handler.evaluateBuf(str);
//        System.out.println(evaluateBuf);
//        long l4 = System.currentTimeMillis();
//        System.out.println(l4 - l3);

        final String max = handler.getMaxLengthSubString("qwertyuiopasdfghjklzxcvbnm");
        System.out.println(max);

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

            InetAddress iaa = InetAddress.getByName("LAPTOP-8KA88UT5");
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

    @Test
    public void testEncrypt() {

        try {
            //16位
            String key = AesUtil.getKey();
            //字符串
            String str = "Do not go gentle into that good night\n" +
                    "\tDylan Thomas\n" +
                    "\t\n" +
                    "Do not go gentle into that good night\n" +
                    "Old age should burn and rave at close of day\n" +
                    "Rage,rage against the dying of the light\n" +
                    "\n" +
                    "Though wise man at their end know dark is right\n" +
                    "Because their words had forked no lightning they\n" +
                    "Do not go gentle into that good night\n" +
                    "\n" +
                    "Good men,the last wave by,crying how bright\n" +
                    "Their frail deeds might have danced in a green bay\n" +
                    "Rage,rage against the dying of the light\n" +
                    "\n" +
                    "Wild men who caught and sang the sun in flight\n" +
                    "And lean,too late,they grieved it on its way\n" +
                    "Do not go gentle into that good night\n" +
                    "\n" +
                    "Grave men,near death,who see with blinding sight\n" +
                    "Blind eyes counld blaze like meteors and be gay\n" +
                    "Rage,rage against the dying of light\n" +
                    "\n" +
                    "And you,myfather,there no the sad height,\n" +
                    "Curse,bless,me now with your fierce tears,I pray\n" +
                    "Do not go gentle into that good night\n" +
                    "Rage,rage against the dying of the light";

            //加密
            String encrypt = AesUtil.encrypt(str, key);
            //解密
            String decrypt = AesUtil.decrypt(encrypt, key);

            System.out.println("key：" + key);
            System.out.println("加密前：" + str);
            System.out.println("加密后：" + encrypt);
            System.out.println("解密后：" + decrypt);


            //公钥加密
            Map<String, String> map = RsaUtil.genKeyPair();
            String publicKey = map.get(RsaUtil.PUBLIC_KEY);
            String ciphertext = RsaUtil.encrypt(str, publicKey);
            //私钥解密
            String privateKey = map.get(RsaUtil.PRIVATE_KEY);
            String plaintext = RsaUtil.decrypt(ciphertext, privateKey);

            System.out.println("公钥：" + publicKey);
            System.out.println("私钥：" + privateKey);
            System.out.println("加密前：" + str);
            System.out.println("加密后：" + ciphertext);
            System.out.println("解密后：" + plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSnow() {
        long nextId = SnowFlakeUtil.nextId();
        System.out.println(nextId);
        System.out.println(Long.MAX_VALUE);
    }

}