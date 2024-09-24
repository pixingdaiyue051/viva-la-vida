package com.tequeno;

import com.tequeno.classload.EnumHandler;
import com.tequeno.classload.KlassHandler;
import com.tequeno.classload.Son;
import org.junit.Test;

public class ClassLoadTest {

    /**
     * Grandpa - 静态代码块
     * Father - 静态代码块
     * Son - 静态代码块
     * Son - 静态方法
     */
    @Test
    public void testStaticRun() {
        Son.staticRun();
    }

    /**
     * Grandpa - 静态代码块
     * Father - 静态代码块
     * Son - 静态代码块
     * Grandpa - 普通代码块
     * Grandpa - 构造方法
     * Father - 普通代码块
     * Father - 构造方法
     * Son - 普通代码块
     * Son - 构造方法
     * Son - 方法
     */
    @Test
    public void testMethodRun() {
        Son son = new Son();
        son.run();
    }

    /**
     * KlassHandler run
     * InnerClass run
     * InnerStaticClass run
     */
    @Test
    public void testInnerClass() {

        KlassHandler handler = new KlassHandler();
        handler.run();

        KlassHandler.InnerClass ki = handler.new InnerClass();
        ki.run();

        KlassHandler.InnerStaticClass kis = new KlassHandler.InnerStaticClass();
        kis.run();
    }

    /**
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法     有多少成员就会调用多少次构造方法
     * EnumHandler 静态代码块    不论有多少成员静态方法只调用一次
     * 01
     * 02
     * 03
     * 04
     * EnumInnerHolder 静态代码块    静态私有内部类-静态代码块有且只会调用一次 适合单例模式
     * 壹
     * 贰
     * 叁
     * 肆
     */
    @Test
    public void testEnum() {
        System.out.println(EnumHandler.ONE.getCode());
        System.out.println(EnumHandler.TWO.getCode());
        System.out.println(EnumHandler.THREE.getCode());
        System.out.println(EnumHandler.FOUR.getCode());
        System.out.println(EnumHandler.getNameByCode(EnumHandler.ONE.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.TWO.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.THREE.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.FOUR.getCode()));
    }

    @Test
    public void testReflect() {
        KlassHandler handler = new KlassHandler();
        handler.reflect();
    }

    @Test
    public void testPath() {
        KlassHandler handler = new KlassHandler();
        handler.resourcePath();
    }
}