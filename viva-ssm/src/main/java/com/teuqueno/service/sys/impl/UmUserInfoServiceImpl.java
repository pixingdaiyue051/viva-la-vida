
package com.teuqueno.service.sys.impl;

import com.teuqueno.entity.sys.UmUserInfo;
import com.teuqueno.mapper.dao.sys.UmUserInfoMapper;
import com.teuqueno.service.base.impl.BaseServiceImpl;
import com.teuqueno.service.sys.UmUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.util.Md5Util;

@Service("umUserInfoService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UmUserInfoServiceImpl extends BaseServiceImpl<UmUserInfoMapper, UmUserInfo> implements UmUserInfoService {

    @Override
    public UmUserInfo selectByNamePwd(String username, String pwd) {
        UmUserInfo uc = dao.selectByNamePwd(username, Md5Util.encode(pwd));
        if (null != uc) {
            jedisUtil.addOrUpdate(uc.getId(), uc);
            return uc;
        } else {
            return null;
        }
    }

    @Override
    public UmUserInfo testTransaction() {
        UmUserInfo u = new UmUserInfo();
        u.setEmail("1111");
        u.setUsername("411111");
        u.setPwd(Md5Util.encode("123456"));
        dao.insertSelective(u);
        return dao.selectByPrimaryKey(u.getId());
    }
}
