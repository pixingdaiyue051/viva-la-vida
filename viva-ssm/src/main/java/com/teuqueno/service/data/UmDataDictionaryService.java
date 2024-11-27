package com.teuqueno.service.data;

import java.util.List;
import java.util.Map;

import com.teuqueno.entity.data.UmDataDictionary;
import com.teuqueno.service.base.BaseService;

public interface UmDataDictionaryService extends BaseService<UmDataDictionary> {
	@Deprecated
	List<String> getAllTypes();

	@Deprecated
	Map<String, List<UmDataDictionary>> getDictMap();

	List<UmDataDictionary> getDetailDict(String typeCode);
}
