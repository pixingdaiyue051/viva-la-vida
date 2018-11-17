package com.tequeno.service;

import com.tequeno.dto.sys.UmUserInfo;

public interface UmUserInfoService extends BaseService<UmUserInfo> {

    UmUserInfo selectByNamePwd(String userName, String pwd);

    UmUserInfo testTransaction();
}
