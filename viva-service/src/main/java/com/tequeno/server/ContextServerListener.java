package com.tequeno.server;

import com.tequeno.service.OneTimeService;
import com.tequeno.service.OneTimeServiceImpl;
import com.tequeno.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ContextServerListener implements ServletContextListener {

//	@Value("${package.to.scan}")
//	private String package;

	// 系统启动的时候，加载基础的数据到系统中
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 系统的根contextroot路径
		ServletContext servletContext = event.getServletContext();
		servletContext.setAttribute(ConstantsUtil.WEBROOT, servletContext.getContextPath());
	}

	@SuppressWarnings("deprecation")
	public void serviceCelled() {
		try {
			OneTimeService hello = new OneTimeServiceImpl();
			OneTimeService stub = (OneTimeService) UnicastRemoteObject.exportObject(hello, 9999);
			LocateRegistry.createRegistry(1099);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("OneTimeService", stub);
			System.out.println("绑定成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
