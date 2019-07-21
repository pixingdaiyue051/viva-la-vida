import com.tequeno.realms.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testAuthentication() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        CustomRealm realm = new CustomRealm();
        // 设置加密算法
        // 使用md5加密
        // 默认加密迭代次数为1
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("md5");
        realm.setCredentialsMatcher(matcher);

        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tequeno", "123456");

        subject.login(token);
//        subject.checkRole("admin");
//        subject.checkPermissions("user:query");

        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();

        Assert.assertFalse(subject.isAuthenticated());

    }
}