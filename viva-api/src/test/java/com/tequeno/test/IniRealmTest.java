package com.tequeno.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testAuthentication() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm realm = new IniRealm("classpath:user.ini");
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tequeno", "123456");

        subject.login(token);
//        subject.checkRole("admin2");
//        subject.checkPermissions("user:query");

        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();

        Assert.assertFalse(subject.isAuthenticated());


    }
}