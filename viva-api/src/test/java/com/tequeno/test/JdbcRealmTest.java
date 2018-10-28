package com.tequeno.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcRealmTest {

    private JdbcRealm realm = new JdbcRealm();

    private DruidDataSource dataSource = new DruidDataSource();

    @Before
    public void setDataSource() {
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/local_test?serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        realm.setDataSource(dataSource);
        realm.setPermissionsLookupEnabled(true);
        // MD5加密算法
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("md5");
        realm.setCredentialsMatcher(matcher);
        // 使用用户名作为盐
        realm.setSaltStyle(JdbcRealm.SaltStyle.EXTERNAL);
    }

    @Test
    public void testAuthentication() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tequeno", "123456");

        subject.login(token);
//        subject.checkRoles("admin","admin2");
//        subject.checkPermissions("user:delete");


        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();

        Assert.assertFalse(subject.isAuthenticated());


    }
}