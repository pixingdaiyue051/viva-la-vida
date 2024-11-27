package org.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.util.ConstantsUtil;

import com.teuqueno.controller.base.BaseController;
import com.teuqueno.entity.sys.UmUserInfo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String webroot = (String) request.getServletContext().getAttribute(ConstantsUtil.WEBROOT);
		UmUserInfo user = (UmUserInfo) session.getAttribute(ConstantsUtil.USER_SESSION);
		if (null == user) {
			logger.warn("=拦截了=");
			response.sendRedirect(webroot + "/um/user/loginPage");
			return false;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
}
