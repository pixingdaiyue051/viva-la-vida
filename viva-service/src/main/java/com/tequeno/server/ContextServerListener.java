package com.tequeno.server;

import com.tequeno.utils.ConstantsUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

public class ContextServerListener implements ServletContextListener {
    private WebApplicationContext springContext;
    private JedisUtil jedisUtil;
    private final String PREFIX = ConstantsUtil.SERVICE_PREFIX;

    // 系统启动的时候，加载基础的数据到系统中
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // 系统的根contextroot路径
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute(ConstantsUtil.WEBROOT, servletContext.getContextPath());
        // 获得spring定义上下文环境，用于获取所有单例的bean
        springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        // 所有已注册的bean
//		String[] beanNames = springContext.getBeanDefinitionNames();
        // 所有service注解的bean
        Map<String, Object> beansWithAnnotation = springContext.getBeansWithAnnotation(Service.class);
        jedisUtil = (JedisUtil) springContext.getBean("jedisUtil");
        this.serviceCelled(beansWithAnnotation, jedisUtil);
    }

    public void serviceCelled(Map<String, Object> beansWithAnnotation, JedisUtil jedisUtil) {
        try {
            for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
                // 获取service存入redis，尝试远程调用
                String beanName = entry.getKey();
                Object bean = entry.getValue();
                jedisUtil.saveOrUpdate(this.getKey(beanName), bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private String getKey(String beanName) {
        return (PREFIX + beanName);
    }
}
