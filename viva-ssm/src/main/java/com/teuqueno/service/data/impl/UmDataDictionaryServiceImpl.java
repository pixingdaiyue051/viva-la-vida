package com.teuqueno.service.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.util.ConstantsUtil;

import com.teuqueno.entity.data.UmDataDictionary;
import com.teuqueno.mapper.dao.data.UmDataDictionaryMapper;
import com.teuqueno.service.base.impl.BaseServiceImpl;
import com.teuqueno.service.data.UmDataDictionaryService;

@Service("umDataDictionaryService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmDataDictionaryServiceImpl extends BaseServiceImpl<UmDataDictionaryMapper, UmDataDictionary>
		implements UmDataDictionaryService {

	@Override
	public List<String> getAllTypes() {
		return dao.getAllTypes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<UmDataDictionary>> getDictMap() {
		Map<String, List<UmDataDictionary>> result = new HashMap<String, List<UmDataDictionary>>();
		try {
			List<String> list = (List<String>) jedisUtil.load(ConstantsUtil.DICT_TYPES);
			for (String typeCode : list) {
				result.put(typeCode, dao.getDictMap(typeCode));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UmDataDictionary> getDetailDict(String typeCode) {
		if (StringUtils.isBlank(typeCode)) {
			return (List<UmDataDictionary>) jedisUtil.load(ConstantsUtil.DICT_ALL);
		} else {
			List<String> list = (List<String>) jedisUtil.load(ConstantsUtil.DICT_TYPES);
			if (list.contains(typeCode)) {
				Map<String, List<UmDataDictionary>> map = (Map<String, List<UmDataDictionary>>) jedisUtil
						.load(ConstantsUtil.DICT_MAP);
				return map.get(typeCode);
			} else {
				return null;
			}
		}
	}

}
