package com.tequeno;

import com.tequeno.utils.AesUtil;
import com.tequeno.utils.RsaUtil;
import org.junit.Test;

import java.util.Map;


public class EncoderTest {

    @Test
    public void run() {

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
}
