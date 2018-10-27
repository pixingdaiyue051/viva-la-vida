package com.tequeno.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcRealmTest {

    private DruidDataSource dataSource = new DruidDataSource();

    @Before
    public void setDataSource() {
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testAuthentication() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        JdbcRealm realm = new JdbcRealm();
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tequeno", "123456");

        subject.login(token);
        subject.checkRole("admin2");
        subject.checkPermissions("user:query");
        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();

        Assert.assertFalse(subject.isAuthenticated());


    }
}