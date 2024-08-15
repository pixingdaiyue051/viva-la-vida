package com.tequeno.annos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AntiRepeatAnno {

    /**
     * 是否手动释放 如果设置 expireTime 则不必设置 releaseManual
     * @return
     */
    boolean manualRelease() default false;

    /**
     * 重复提交时间，即在该时段内只能提交一次
     *
     * @return
     */
    long expireTime() default 3000L;
}