package com.tequeno.service;

import com.github.pagehelper.PageInfo;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public interface BaseService<T> extends Remote {
	T selectByPrimaryKey(String id);

	@Deprecated
	List<T> selectAll();

	int insertSelective(T entity) throws Exception;

	int updateSelective(T entity) throws Exception;

	int deleteByPrimaryKey(String id) throws Exception;

	PageInfo<T> findPager(Map<String, Object> map, Integer currentPage, Integer limit, String methodName);

	PageInfo<Map<String, Object>> findPagerMap(Map<String, Object> map, Integer currentPage, Integer limit,
			String methodName);

}