package com.teuqueno.service.sys.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teuqueno.entity.sys.UmResourceInfo;
import com.teuqueno.mapper.dao.sys.UmResourceInfoMapper;
import com.teuqueno.service.base.impl.BaseServiceImpl;
import com.teuqueno.service.sys.UmResourceInfoService;

@Service("umResourceInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmResourceInfoServiceImpl extends BaseServiceImpl<UmResourceInfoMapper, UmResourceInfo>
		implements UmResourceInfoService {

}
