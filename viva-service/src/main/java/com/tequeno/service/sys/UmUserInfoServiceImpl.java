
package com.tequeno.service.sys;

import com.tequeno.dto.sys.UmUserInfo;
import com.tequeno.mapper.dao.sys.UmUserInfoMapper;
import com.tequeno.service.UmUserInfoService;
import com.tequeno.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("umUserInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmUserInfoServiceImpl extends BaseServiceImpl<UmUserInfoMapper, UmUserInfo> implements UmUserInfoService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -992954323489525830L;

    @Override
    public UmUserInfo selectByNamePwd(String username, String pwd) {
        return dao.selectByNamePwd(username, pwd);
    }

    @Override
    public UmUserInfo testTransaction() {
        UmUserInfo u = new UmUserInfo();
        u.setEmail("1111");
        u.setUsername("411111");
//        u.setPwd(Md5Util.encode("123456"));
        dao.insertSelective(u);
        return dao.selectByPrimaryKey(u.getId());
    }

}
