package com.teuqueno.service.sys;

import com.teuqueno.entity.sys.UmUserInfo;
import com.teuqueno.service.base.BaseService;

public interface UmUserInfoService extends BaseService<UmUserInfo> {

    UmUserInfo selectByNamePwd(String userName, String pwd);

    UmUserInfo testTransaction();
}
