package com.tequeno;

import com.tequeno.net.HttpClientHandler;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class HttpTest {

    @Test
    public void url() {
        HttpClientHandler handler = new HttpClientHandler();
        handler.url("http://127.0.0.1:7210/emp/page?pageNum=1&pageSize=5");
    }

    @Test
    public void get() {
        HttpClientHandler handler = new HttpClientHandler();
        handler.get("http://127.0.0.1:7210/emp/page?pageNum=1&pageSize=5");
    }

    @Test
    public void postJson() {
        HttpClientHandler handler = new HttpClientHandler();

        Map<String, String> map = Map.of("pageNum", "1", "pageSize", "5");
        handler.postJson("http://127.0.0.1:7210/emp/upt", map);
    }

    @Test
    public void postFormUrlencoded() {
        HttpClientHandler handler = new HttpClientHandler();

        Map<String, String> map = Map.of("pageNum", "1", "pageSize", "5");
        handler.postFormUrlencoded("http://127.0.0.1:7210/emp/page", map);
    }

    @Test
    public void postMultiFormData() {
        HttpClientHandler handler = new HttpClientHandler();

        Map<String, String> map = Map.of("pageNum", "1", "pageSize", "5");
        Map<String, File> fileMap = Map.of("file", new File("/data/doc/test.zip"));
        handler.postMultiFormData("http://127.0.0.1:8080/user", map, fileMap);
    }

}