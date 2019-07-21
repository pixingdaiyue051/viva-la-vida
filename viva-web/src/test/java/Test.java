import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {

    @org.junit.Test
    public void test() {
        Md5Hash md5 = new Md5Hash("123456", "tequeno");
        System.out.println(md5.toString());
    }
}