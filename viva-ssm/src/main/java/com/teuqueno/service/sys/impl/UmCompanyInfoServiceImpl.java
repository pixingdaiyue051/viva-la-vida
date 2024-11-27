package com.teuqueno.service.sys.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teuqueno.entity.sys.UmCompanyInfo;
import com.teuqueno.mapper.dao.sys.UmCompanyInfoMapper;
import com.teuqueno.service.base.impl.BaseServiceImpl;
import com.teuqueno.service.sys.UmCompanyInfoService;

@Service("umCompanyInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmCompanyInfoServiceImpl extends BaseServiceImpl<UmCompanyInfoMapper, UmCompanyInfo>
		implements UmCompanyInfoService {

}
