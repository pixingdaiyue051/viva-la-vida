package com.teuqueno.mapper.dao.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.teuqueno.entity.data.UmDataDictionary;

import tk.mybatis.mapper.common.Mapper;

public interface UmDataDictionaryMapper extends Mapper<UmDataDictionary> {
	List<String> getAllTypes();

	List<UmDataDictionary> getDictMap(@Param("typeCode") String typeCode);
}