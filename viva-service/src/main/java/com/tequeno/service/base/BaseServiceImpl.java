package com.tequeno.service.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.component.JedisUtil;
import org.component.SolrUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tequeno.service.BaseService;

import tk.mybatis.mapper.common.Mapper;

public class BaseServiceImpl<D extends Mapper<T>, T> implements BaseService<T> {
	@Autowired
	protected D dao;

	@Autowired
	protected JedisUtil jedisUtil;

	@Autowired
	protected SolrUtil solrUtil;

	@SuppressWarnings("unchecked")
	@Override
	public T selectByPrimaryKey(String id) {
		Object o = jedisUtil.load(id);
		if (null != o) {
			return (T) o;
		} else {
			T t = dao.selectByPrimaryKey(id);
			jedisUtil.addOrUpdate(id, t);
			return t;
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		int result = dao.deleteByPrimaryKey(id);
		if (result > 0) {
			if (jedisUtil.check(id)) {
				jedisUtil.delete(id);
			}
		}
		return result;
	}

	@Override
	public List<T> selectAll() {
		return dao.selectAll();
	}

	@Override
	public int insertSelective(T entity) throws Exception {
		int result = dao.insertSelective(entity);
		if (result > 0) {
			Method mGetId = entity.getClass().getMethod("getId");
			jedisUtil.addOrUpdate((String) mGetId.invoke(entity), entity);
		}
		return result;
	}

	@Override
	public int updateSelective(T entity) throws Exception {
		int result = dao.updateByPrimaryKeySelective(entity);
		if (result > 0) {
			Method mGetId = entity.getClass().getMethod("getId");
			jedisUtil.addOrUpdate((String) mGetId.invoke(entity), entity);
		}
		return result;
	}

	@Override
	public String insertSelectiveFetchId(T entity) throws Exception {
		int result = dao.insertSelective(entity);
		String id = "";
		if (result > 0) {
			Method mGetId = entity.getClass().getMethod("getId");
			id = (String) mGetId.invoke(entity);
			jedisUtil.addOrUpdate(id, entity);
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<T> findPager(Map<String, Object> map, Integer currentPage, Integer limit, String method) {
		Page<T> pages = null;
		if (null != currentPage && null != limit) {
			pages = PageHelper.startPage(currentPage, limit);
		}
		List<T> listData = null;
		try {
			if (StringUtils.isBlank(method)) {
				method = "selectAllByCondition";
			}
			Method m = dao.getClass().getMethod(method, Map.class);
			listData = (List<T>) m.invoke(dao, map);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		PageInfo<T> pageInfo = new PageInfo<T>(pages);
		pageInfo.setList(listData);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<Map<String, Object>> findPagerMap(Map<String, Object> map, Integer currentPage, Integer limit,
			String methodName) {
		Page<Map<String, Object>> pages = null;
		if (null != currentPage && null != limit) {
			pages = PageHelper.startPage(currentPage, limit);
		}
		List<Map<String, Object>> listData = null;
		try {
			if (StringUtils.isBlank(methodName)) {
				methodName = "selectMapByCondition";
			}
			Method m = dao.getClass().getMethod(methodName, Map.class);
			listData = (List<Map<String, Object>>) m.invoke(dao, map);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pages);
		pageInfo.setList(listData);
		return pageInfo;
	}

}