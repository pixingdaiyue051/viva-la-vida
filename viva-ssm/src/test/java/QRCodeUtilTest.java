import junit.framework.TestCase;
import org.util.qrcode.QRCodeUtil;

import java.util.Map;

public class QRCodeUtilTest extends TestCase {

    public void testWrit2File() {
        Map<String, Object> map = QRCodeUtil.writ2File("https://www.baidu.com", "", "/data/pic/", "");
        String code = map.get("code").toString();
        String msg = map.get("msg").toString();
        System.out.println(code);
        System.out.println(msg);
    }

    public void testWrit2Stream() {
    }

    public void testDecode() {
    }
}