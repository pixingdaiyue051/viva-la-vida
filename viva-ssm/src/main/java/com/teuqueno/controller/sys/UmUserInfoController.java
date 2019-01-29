package com.teuqueno.controller.sys;

import com.teuqueno.controller.base.BaseController;
import com.teuqueno.entity.sys.UmUserInfo;
import com.teuqueno.service.sys.UmUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.util.ConstantsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/um/user")
public class UmUserInfoController extends BaseController {
	@Autowired
	private UmUserInfoService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("deprecation")
		List<UmUserInfo> userList = userService.selectAll();
		request.setAttribute("userList", userList);
		return "user/user_list";
	}

	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public String one(HttpServletRequest request, HttpServletResponse response, String id) {
		UmUserInfo user = userService.selectByPrimaryKey(id);
		request.setAttribute("user", user);
		return "user/user_one";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ResponseBody
	public UmUserInfo add(HttpServletRequest request, HttpServletResponse response) {
		try {
			UmUserInfo user = new UmUserInfo();
//			user.setUserName("new1");
//			user.setTrueName("new1");
//			user.setPwd("new1");
//			userService.insertSelectiveFetchId(user);
			user = userService.testTransaction();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public int delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			UmUserInfo user = new UmUserInfo();
			user.setUserName("new1");
			user.setTrueName("new1");
			user.setPwd("new1");
			userService.insertSelectiveFetchId(user);
			return userService.deleteByPrimaryKey(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response) {
		return "user/user_login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, UmUserInfo user) {
		try {
			user = userService.selectByNamePwd(user.getUserName(), user.getPwd());
			if (null != user) {
				request.getSession().setAttribute(ConstantsUtil.USER_SESSION, user);
				return "redirect:/um/user/one.do?id=" + user.getId();
			}
			return "user/user_login";
		} catch (Exception e) {
			e.printStackTrace();
			return "user/user_login";
		}
	}
}
