package com.tequeno.service.sys;

import com.tequeno.mapper.dao.sys.UmRoleInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tequeno.dto.sys.UmRoleInfo;
import com.tequeno.service.base.BaseServiceImpl;
import com.tequeno.service.UmRoleInfoService;

@Service("umRoleInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmRoleInfoServiceImpl extends BaseServiceImpl<UmRoleInfoMapper, UmRoleInfo> implements UmRoleInfoService {

}
