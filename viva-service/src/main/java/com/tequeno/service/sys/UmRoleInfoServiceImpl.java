package com.tequeno.service.sys;

import com.tequeno.dto.sys.UmRoleInfo;
import com.tequeno.mapper.dao.sys.UmRoleInfoMapper;
import com.tequeno.service.UmRoleInfoService;
import com.tequeno.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("umRoleInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmRoleInfoServiceImpl extends BaseServiceImpl<UmRoleInfoMapper, UmRoleInfo> implements UmRoleInfoService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -893429438242385282L;
}
