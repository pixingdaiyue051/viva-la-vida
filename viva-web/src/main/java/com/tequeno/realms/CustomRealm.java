package com.tequeno.realms;

import com.tequeno.dto.sys.UmUserInfo;
import com.tequeno.redis.JedisUtil;
import com.tequeno.service.UmUserInfoService;
import com.tequeno.utils.ConstantsUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    protected JedisUtil jedisUtil;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = this.getRolesByUsername(username);
        Set<String> permissions = this.getPermissionsByRoles();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        UmUserInfo user = this.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPwd(), "custonRealm");
        // 使用用户名作为盐加密
        info.setCredentialsSalt(ByteSource.Util.bytes(username));
        return info;
    }

    private UmUserInfo getUserByUsername(String username) {
        UmUserInfoService umUserInfoService = (UmUserInfoService) jedisUtil.get(ConstantsUtil.SERVICE_PREFIX + "umUserInfoService");
        UmUserInfo user = umUserInfoService.selectByNamePwd(username, null);
        return user;
    }

    private Set<String> getRolesByUsername(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        return roles;
    }

    private Set<String> getPermissionsByRoles() {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:query");
        return permissions;
    }

    public JedisUtil getJedisUtil() {
        return jedisUtil;
    }

    public void setJedisUtil(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }
}
