package com.teuqueno.controller.base;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.component.JedisUtil;
import org.component.SolrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.util.ConstantsUtil;
import org.util.JsonUtil;
import org.util.StatesUtil;
import org.util.ToolsUtil;

import com.teuqueno.entity.data.UmDataDictionary;
import com.teuqueno.service.data.UmDataDictionaryService;

@Controller
@RequestMapping("/base")
public class BaseController {
	@Autowired
	protected JedisUtil jedisUtil;

	@Autowired
	protected SolrUtil solrUtil;

	@Autowired
	protected UmDataDictionaryService dictService;

	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String backendIndex(HttpServletRequest request, HttpServletResponse response) {
		return "system/backend_index";
	}

	@RequestMapping(value = "/dict", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<UmDataDictionary> dict(HttpServletRequest request, HttpServletResponse response, String typeCode) {
		return dictService.getDetailDict(typeCode);
	}

	@RequestMapping(value = "/i18n", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> i18n(HttpServletRequest request, HttpServletResponse response, String language) {
		Map<String, Object> map = ToolsUtil.refreshI18n(language);
		request.getSession().setAttribute(ConstantsUtil.LANGUAGE, language);
		request.getSession().setAttribute(ConstantsUtil.LANGUAGE_MAP, map);
		request.getSession().setAttribute(ConstantsUtil.LANGUAGE_OBJECT, JsonUtil.toJsonString(map));
		logger.warn("=国际化信息加载完成=");
		return map;
	}

	@RequestMapping(value = "/i18nNew")
	public String i18nNew(HttpServletRequest request, Model model, HttpServletResponse response, String language) {
		Locale locale = null;
		switch (language) {
		case StatesUtil.FT:
			locale = Locale.TAIWAN;
			break;
		case StatesUtil.YW:
			locale = Locale.US;
			break;
		case StatesUtil.ZW:
			locale = Locale.CHINA;
			break;
		default:
			locale = LocaleContextHolder.getLocale();
			break;
		}
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		logger.warn("=国际化信息加载完成=");
		return "redirect:/um/user/loginPage.do";
	}

}