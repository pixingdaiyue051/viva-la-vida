package org.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.util.ConstantsUtil;
import org.util.JsonUtil;
import org.util.StatesUtil;
import org.util.ToolsUtil;

public class I18nInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String language = (String) session.getAttribute(ConstantsUtil.LANGUAGE);
		if (StringUtils.isBlank(language)) {
			Map<String, Object> map = ToolsUtil.refreshI18n(StatesUtil.ZW);
			session.setAttribute(ConstantsUtil.LANGUAGE, language);
			session.setAttribute(ConstantsUtil.LANGUAGE_MAP, map);
			session.setAttribute(ConstantsUtil.LANGUAGE_OBJECT, JsonUtil.toJsonString(map));
		}
		return super.preHandle(request, response, handler);
	}
}
