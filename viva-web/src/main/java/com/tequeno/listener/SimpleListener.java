package com.tequeno.listener;

import com.tequeno.handler.FileHandler;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听器
 * 一类监听器：监听域对象的创建、销毁
 *   ServletContextListener 监听ServletContext域对象的创建、销毁
 *   HttpSessionListener  监听HttpSession域对象的创建、销毁
 *   ServletRequestListener  监听ServletRequest域对象的创建、销毁
 * 二类监听器：监听域对象中的属性变更（属性设置、属性替换、属性移除）
 *   ServletContextAttributeListener
 *   HttpSessionAttributeListener
 *   ServletRequestAttributeListener
 * 三类监听器：监听域对象中的java对象的绑定
 *   HttpSessionBindingListener
 *   HttpSessionIdListener
 */
@WebListener
@Slf4j
public class SimpleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("context start...");
        FileHandler.getInstance().initDir();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("context destroy...");
    }
}
