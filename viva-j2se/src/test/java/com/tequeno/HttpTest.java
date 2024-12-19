package com.tequeno;

import com.tequeno.net.InetConst;
import com.tequeno.net.http.HttpClientHandler;
import com.tequeno.net.http.UrlHandler;
import org.junit.Test;

import java.util.Map;

public class HttpTest {

    @Test
    public void url() {
        UrlHandler u = new UrlHandler();
        u.run("https://www.baidu.com");
        u.run("https://api.seniverse.com/v3/weather/daily.json?key=SgHRgNgQwh3CCTTTF&location=hangzhou&language=zh-Hans&unit=c&start=-1&days=5");
    }

    @Test
    public void client() {
        HttpClientHandler handler = new HttpClientHandler();
//        Map<String, Object> map = Map.of("pageNum", 1, "pageSize", "5");
//        handler.get("http://127.0.0.1:7210/emp/page?pageNum=1&pageSize=5");
//        handler.postJson("http://127.0.0.1:7210/emp/page", map);
//        handler.postFormUrlencoded("http://127.0.0.1:7210/emp/page", map);

        handler.postMultiForm("http://127.0.0.1:7210/file/upload/single");
    }

}
