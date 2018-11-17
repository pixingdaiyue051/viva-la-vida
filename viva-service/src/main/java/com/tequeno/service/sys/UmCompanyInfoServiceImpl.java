package com.tequeno.service.sys;

import com.tequeno.mapper.dao.sys.UmCompanyInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tequeno.dto.sys.UmCompanyInfo;
import com.tequeno.service.base.BaseServiceImpl;
import com.tequeno.service.UmCompanyInfoService;

@Service("umCompanyInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmCompanyInfoServiceImpl extends BaseServiceImpl<UmCompanyInfoMapper, UmCompanyInfo>
		implements UmCompanyInfoService {

}
