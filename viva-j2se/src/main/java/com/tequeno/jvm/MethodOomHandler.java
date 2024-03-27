package com.tequeno.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.HashSet;

/**
 * 方法区内存溢出测试
 * -XX:PermSize=6m 永久代内存大小
 * -XX:MaxPermSize=6m 永久代最大内存大小
 * <p>
 * -XX:MetaSpaceSize=6m 元空间初始内存大小 达到该值就会触发gc并根据gc力度重新调整
 * -XX:MaxMetaSpaceSize=6m 元空间最大内存大小 如果不设置就默认和物理内存一致
 * -XX:MinMetaSpaceFreeRatio 最小剩余容量百分比
 * -XX:MaxMetaSpaceFreeRatio 最大剩余容量百分比
 * <p>
 * 从jdk8开始 元空间(metaspace)就完全替代了永久代(permspace)
 * 从jdk7开始 字符串常量池就从方法区的常量池表中移出到堆内存中
 */
public class MethodOomHandler {

    /**
     * 常量池内存溢出
     * 由于从jdk7开始 字符串常量池就从方法区的常量池表中移出到堆内存中 再设置permsize已经没有意义了
     * VM args: -Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError
     */
    public void constantPool() {
        HashSet<String> set = new HashSet<>();
        int i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }


    /**
     * String.intern
     * 如果字符串常量池有该字符串则返回常量池中该字符串引用
     * 如果没有则新建并加入常量池再返回常量池中该字符串引用
     */
    public void stringIntern() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    /**
     * 方法区的主要作用时存放和类型相关的信息 比如类名，修饰符，常量池，字段描述，方法描述...
     * 只要大量生成类而不回收就会出现溢出
     * <p>
     * 使用cglib对oomObject类进行增强 动态创建
     */
    public void dynamicGenKlass() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OomObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, objects));
            enhancer.create();
        }
    }
}