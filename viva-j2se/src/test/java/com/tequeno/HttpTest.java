package com.tequeno;

import com.tequeno.net.http.HttpClientHandler;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class HttpTest {

    @Test
    public void run() {
        HttpClientHandler handler = new HttpClientHandler();
//        handler.url("http://127.0.0.1:7210/emp/page?pageNum=1&pageSize=5");
//        handler.get("http://127.0.0.1:7210/emp/page?pageNum=1&pageSize=5");

        Map<String, String> map = Map.of("pageNum", "1", "pageSize", "5");
//        handler.postFormUrlencoded("http://127.0.0.1:7210/emp/page", map);
//        handler.postJson("http://127.0.0.1:7210/emp/upt", map);

        Map<String, File> fileMap = Map.of("file", new File("/data/doc/test.zip"));
        handler.postMultiFormData("http://127.0.0.1:8080/user", map, fileMap);
    }

}