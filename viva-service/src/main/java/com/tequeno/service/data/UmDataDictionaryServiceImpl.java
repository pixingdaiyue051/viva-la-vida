package com.tequeno.service.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tequeno.dto.data.UmDataDictionary;
import com.tequeno.mapper.dao.data.UmDataDictionaryMapper;
import com.tequeno.service.base.BaseServiceImpl;
import com.tequeno.service.UmDataDictionaryService;

@Service("umDataDictionaryService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmDataDictionaryServiceImpl extends BaseServiceImpl<UmDataDictionaryMapper, UmDataDictionary>
		implements UmDataDictionaryService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -562928953523582534L;

	@Override
	public List<String> getAllTypes() {
		return dao.getAllTypes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<UmDataDictionary>> getDictMap() {
		Map<String, List<UmDataDictionary>> result = new HashMap<String, List<UmDataDictionary>>();
		try {
//			List<String> list = (List<String>) jedisUtil.load(ConstantsUtil.DICT_TYPES);
//			for (String typeCode : list) {
//				result.put(typeCode, dao.getDictMap(typeCode));
//			}
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UmDataDictionary> getDetailDict(String typeCode) {
//		if (StringUtils.isBlank(typeCode)) {
////			return (List<UmDataDictionary>) jedisUtil.load(ConstantsUtil.DICT_ALL);
//		} else {
////			List<String> list = (List<String>) jedisUtil.load(ConstantsUtil.DICT_TYPES);
//			if (list.contains(typeCode)) {
//				Map<String, List<UmDataDictionary>> map = (Map<String, List<UmDataDictionary>>) jedisUtil
//						.load(ConstantsUtil.DICT_MAP);
//				return map.get(typeCode);
//			} else {
//				return null;
//			}
//		}
		return null;
	}

}
