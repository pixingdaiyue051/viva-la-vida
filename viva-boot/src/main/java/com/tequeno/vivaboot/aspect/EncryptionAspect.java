package com.tequeno.vivaboot.aspect;

import com.alibaba.fastjson.JSON;
import com.tequeno.annos.EncryptionAnno;
import com.tequeno.utils.AesUtil;
import com.tequeno.utils.RsaUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AES + RSA 加解密AOP处理
 */
@Component
@Aspect
public class EncryptionAspect {

    private final static Logger log = LoggerFactory.getLogger(EncryptionAspect.class);

    @Pointcut("@annotation(com.tequeno.annos.EncryptionAnno)")
    public void aspect() {
    }

    @Around("aspect() && @annotation(anno)")
    public Object run(ProceedingJoinPoint joinPoint, EncryptionAnno anno) throws Throwable {
        if (anno.value().equals(EncryptionAnno.Type.ENCRYPT)) {
            return this.after(joinPoint.proceed());
        }
        if (anno.value().equals(EncryptionAnno.Type.HANDSHAKE)) {
            return this.after(joinPoint.proceed(this.pre(joinPoint)));
        }
        if (anno.value().equals(EncryptionAnno.Type.DECRYPT)) {
            return joinPoint.proceed(this.pre(joinPoint));
        }
        return joinPoint.proceed();
    }

    private Object[] pre(ProceedingJoinPoint joinPoint) throws Exception {
        long l1 = System.currentTimeMillis();

        Object[] args = joinPoint.getArgs();
        Map<String, String> req = (Map<String, String>) args[0];
        String cipher = req.get("cipher");
        String encryptData = req.get("data");
        // 使用后端私钥解密得到aesKey
        String privateKey = RsaUtil.genKeyPair().get(RsaUtil.PRIVATE_KEY);
        String aesKey = RsaUtil.decrypt(cipher, privateKey);
        // 使用aesKey解密数据
        String decrypt = AesUtil.decrypt(encryptData, aesKey);

        Object o = JSON.parseObject(decrypt, Map.class);
        args[0] = o;

        long l2 = System.currentTimeMillis();
        log.info("解密时长[{}]", l2 - l1);
        return args;
    }

    private Map<String, String> after(Object o) throws Throwable {
        long l1 = System.currentTimeMillis();

        Map<String, String> result = (Map<String, String>) o;

        String res1 = JSON.toJSONString(result.get("data"));
        // 使用aesKey加密数据
        String key = AesUtil.getKey();
        String data = AesUtil.encrypt(res1, key);
        // 使用前端公钥加密aesKey
        String jpk = RsaUtil.genKeyPair().get(RsaUtil.JS_PUBLIC_KEY);
        String cipher = RsaUtil.encrypt(key, jpk);

        result.put("data", data);
        result.put("cipher", cipher);
        result.put("code", "0");

        long l2 = System.currentTimeMillis();
        log.info("加密时长[{}]", l2 - l1);
        return result;
    }
}
