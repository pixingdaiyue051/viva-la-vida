package com.tequeno.vivaboot.aspect;

import com.tequeno.annos.AntiRepeatAnno;
import com.tequeno.dto.HtCommonException;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.vivaboot.config.redis.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 注解实现方法级别的防重复提交,意义不大
 */
@Component
@Aspect
@Slf4j
public class AntiRepeatAspect {

    @Pointcut("@annotation(com.tequeno.annos.AntiRepeatAnno)")
    public void aspect() {
    }

    @Around("aspect() && @annotation(anno)")
    public Object run(ProceedingJoinPoint joinPoint, AntiRepeatAnno anno) throws Throwable {

        String key = joinPoint.getSignature().getDeclaringTypeName() + joinPoint.getSignature().getName();
        String token = String.valueOf(System.currentTimeMillis());
        long expireTime = anno.expireTime();
        log.info("key:[{}],token[{}], expireTime:[{}]", key, token, expireTime);

        JedisUtil jedisUtil = JedisUtil.getInstance();
        boolean locked = jedisUtil.luaTryLock(key, token, expireTime);
        if (!locked) {
            throw new HtCommonException(HtCommonErrorEnum.LATER_TO_RETRY);
        }
        Object proceed = joinPoint.proceed();
        if (anno.manualRelease()) {
            jedisUtil.luaReleaseLock(key, token);
        }
        return proceed;
    }
}
