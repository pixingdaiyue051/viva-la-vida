package org.util;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.component.JedisUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.teuqueno.entity.data.UmDataDictionary;
import com.teuqueno.service.data.UmDataDictionaryService;

public class ContextParamListener implements ServletContextListener {
	private WebApplicationContext springContext;
	private JedisUtil jedisUtil;
	private UmDataDictionaryService dictService;

	// 系统启动的时候，加载基础的数据到系统中
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 系统的根contextroot路径
		ServletContext servletContext = event.getServletContext();
		servletContext.setAttribute(ConstantsUtil.WEBROOT, servletContext.getContextPath());
		springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		jedisUtil = (JedisUtil) springContext.getBean("jedisUtil");
		dictService = (UmDataDictionaryService) springContext.getBean("umDataDictionaryService");
		dataInitialized();
	}

	@SuppressWarnings("deprecation")
	public void dataInitialized() {
		jedisUtil.addOrUpdate(ConstantsUtil.DICT_TYPES, dictService.getAllTypes());
		Map<String, List<UmDataDictionary>> map = dictService.getDictMap();
		if (!map.isEmpty()) {
			jedisUtil.addOrUpdate(ConstantsUtil.DICT_MAP, map);
		}
		jedisUtil.addOrUpdate(ConstantsUtil.DICT_ALL, dictService.selectAll());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
