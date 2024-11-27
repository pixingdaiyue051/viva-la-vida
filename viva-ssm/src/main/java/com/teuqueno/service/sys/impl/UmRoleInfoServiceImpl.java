package com.teuqueno.service.sys.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teuqueno.entity.sys.UmRoleInfo;
import com.teuqueno.mapper.dao.sys.UmRoleInfoMapper;
import com.teuqueno.service.base.impl.BaseServiceImpl;
import com.teuqueno.service.sys.UmRoleInfoService;

@Service("umRoleInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmRoleInfoServiceImpl extends BaseServiceImpl<UmRoleInfoMapper, UmRoleInfo> implements UmRoleInfoService {

}
