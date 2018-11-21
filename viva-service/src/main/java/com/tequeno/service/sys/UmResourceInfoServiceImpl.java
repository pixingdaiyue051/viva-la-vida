package com.tequeno.service.sys;

import com.tequeno.mapper.dao.sys.UmResourceInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tequeno.dto.sys.UmResourceInfo;
import com.tequeno.service.base.BaseServiceImpl;
import com.tequeno.service.UmResourceInfoService;

import java.io.Serializable;

@Service("umResourceInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmResourceInfoServiceImpl extends BaseServiceImpl<UmResourceInfoMapper, UmResourceInfo>
		implements UmResourceInfoService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -318492348234238432L;
}
