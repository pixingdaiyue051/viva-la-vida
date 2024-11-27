package com.teuqueno.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.component.JedisUtil;
import org.component.SolrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.util.ConstantsUtil;
import org.util.ToolsUtil;

import com.teuqueno.entity.data.UmDataDictionary;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
	private SolrUtil solrUtil;

	private final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "test/index";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSolr", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addSolr(HttpServletRequest request, HttpServletResponse response, String language) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UmDataDictionary> dics = (List<UmDataDictionary>) jedisUtil.load(ConstantsUtil.DICT_ALL);
			List<SolrInputDocument> solrInputDocList = ToolsUtil.castToSolrInputDocList(dics);
			solrUtil.saveList(solrInputDocList);
			solrInputDocList.clear();
			List<Map<String, Object>> maps = null;
			solrInputDocList = ToolsUtil.castToSolrInputDocList(maps);
			solrUtil.saveList(solrInputDocList);
			map.clear();
			map.put("state", 1);
			map.put("msg", "保存成功");
			logger.warn("=solr添加成功=");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=solr添加失败=" + e.getMessage());
			map.clear();
			map.put("state", 0);
			map.put("msg", "保存失败");
		}
		return map;
	}

	@RequestMapping(value = "/delSolr", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> delSolr(HttpServletRequest request, HttpServletResponse response, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(id)) {
				solrUtil.deleteAll();
			} else {
				solrUtil.deleteById(id);
			}
			map.put("state", 1);
			map.put("msg", "删除成功");
			logger.warn("=solr删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=solr删除失败=" + e.getMessage());
			map.clear();
			map.put("state", 0);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@RequestMapping(value = "/searchSolr", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> searchSolr(HttpServletRequest request, HttpServletResponse response, String searchParam,
			boolean isPager, Integer pageIndex, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> fields = new ArrayList<String>();
			fields.add("typeName");
			fields.add("typeCode");
			fields.add("valueName");
			fields.add("valueCode");
			fields.add("goodsCode");
			fields.add("goodsName");
			fields.add("goodsUnit");
			fields.add("goodsModel");
			fields.add("goodsDesc");
			if (isPager) {
				map = this.luceneSelect(request, response, searchParam, pageIndex, pageSize, null, null, fields);
			} else {
				map = this.luceneAll(request, response, searchParam, null, null, fields);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=solr查询失败=" + e.getMessage());
			map.clear();
			map.put(ConstantsUtil.GRID_TOTAL, 0);
			map.put(ConstantsUtil.GRID_DATA, null);
		}
		return map;
	}

	private Map<String, Object> luceneSelect(HttpServletRequest request, HttpServletResponse response,
			String searchParam, Integer pageIndex, Integer pageSize, String field, String order,
			List<String> highLightFields) {
		StringBuffer buf = new StringBuffer();
		if (StringUtils.isBlank(searchParam)) {
			buf.append(ConstantsUtil.LUCENE_DEFAULT);
		} else {
//			buf.append(ConstantsUtil.LUCENE_GOODS + searchParam);
			String[] params = searchParam.split(ConstantsUtil.LUCENE_SPLIT);
			for (String param : params) {
				if (StringUtils.isBlank(param)) {
					continue;
				}
				buf.append(ConstantsUtil.LUCENE_ALL + param + " AND ");
			}
			buf.delete(buf.length() - 5, buf.length());
		}
		if (null == pageIndex || pageIndex < 0) {
			pageIndex = 0;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = 10;
		}
		if (StringUtils.isBlank(field)) {
			field = "score";
			order = "desc";
		} else {
			if (StringUtils.isBlank(order)) {
				order = "asc";
			}
		}
		SolrDocumentList docs = solrUtil.search(buf.toString(), pageIndex, pageSize, field, order, highLightFields);
		List<SolrDocument> list = null;
		long total = docs.getNumFound();
		if (total > 0l) {
			list = docs;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ConstantsUtil.GRID_TOTAL, total);
		map.put(ConstantsUtil.GRID_DATA, list);
		return map;
	}

	private Map<String, Object> luceneAll(HttpServletRequest request, HttpServletResponse response, String searchParam,
			String field, String order, List<String> highLightFields) {
		StringBuffer buf = new StringBuffer();
		if (StringUtils.isBlank(searchParam)) {
			buf.append(ConstantsUtil.LUCENE_DEFAULT);
		} else {
			String[] params = searchParam.split(ConstantsUtil.LUCENE_SPLIT);
			for (String param : params) {
				if (StringUtils.isBlank(param)) {
					continue;
				}
				buf.append(ConstantsUtil.LUCENE_ALL + param + " AND ");
			}
			buf.delete(buf.length() - 5, buf.length());
		}
		if (StringUtils.isBlank(field)) {
			field = "_version_";
			order = "asc";
		} else {
			if (StringUtils.isBlank(order)) {
				order = "asc";
			}
		}
		SolrDocumentList docs = solrUtil.search(buf.toString(), 0, Integer.MAX_VALUE, field, order, highLightFields);
		List<SolrDocument> list = null;
		long total = docs.getNumFound();
		if (total > 0l) {
			list = docs;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ConstantsUtil.GRID_TOTAL, total);
		map.put(ConstantsUtil.GRID_DATA, list);
		return map;
	}

}