import junit.framework.TestCase;
import org.util.Md5Util;

public class Md5UtilTest extends TestCase {

    public void testEncode() {
        String pwd = Md5Util.encode("aaa");
        System.out.println(pwd);
        pwd = Md5Util.encode("bbb");
        System.out.println(pwd);
        pwd = Md5Util.encode("ccc");
        System.out.println(pwd);
    }
}