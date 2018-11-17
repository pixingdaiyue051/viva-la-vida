package com.tequeno.service;

import java.util.List;
import java.util.Map;

import com.tequeno.dto.data.UmDataDictionary;

public interface UmDataDictionaryService extends BaseService<UmDataDictionary> {
	@Deprecated
	List<String> getAllTypes();

	@Deprecated
	Map<String, List<UmDataDictionary>> getDictMap();

	List<UmDataDictionary> getDetailDict(String typeCode);
}
