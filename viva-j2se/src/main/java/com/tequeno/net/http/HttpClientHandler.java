package com.tequeno.net.http;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class HttpClientHandler {

    private final static Logger log = LoggerFactory.getLogger(HttpClientHandler.class);

    public void get(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (200 != res.statusCode()) {
                log.error("请求失败");
                return;
            }
            String body = res.body();
            log.info("结果:{}", body);

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public void postJson(String url, Map<String, Object> map) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(map), StandardCharsets.UTF_8))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (200 != res.statusCode()) {
                log.error("请求失败");
                return;
            }
            String body = res.body();
            log.info("结果:{}", body);

//            client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
//                    .thenApply(HttpResponse::body)
//                    .thenAccept(out -> log.info("结果:{}", out))
//                    .join();
//
//            String out = client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
//                    .get()
//                    .body();
//            log.info("结果:{}", out);

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public void postFormUrlencoded(String url, Map<String, Object> map) {
        try {
            StringBuilder builder = new StringBuilder();
            map.forEach((k, v) -> builder.append(k).append("=").append(v).append("&"));
            builder.deleteCharAt(builder.length() - 1);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(builder.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (200 != res.statusCode()) {
                log.error("请求失败");
                return;
            }
            String body = res.body();
            log.info("结果:{}", body);

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public void postMultiForm(String url) {
        try (InputStream is = Files.newInputStream(Paths.get("/data/doc/test.zip"), StandardOpenOption.READ);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            bos.write("Content-Disposition:form-data;name=\"file\"".getBytes(StandardCharsets.UTF_8));
            bos.write("Content-Type:application/octet-stream".getBytes(StandardCharsets.UTF_8));
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                bos.write(b, 0, len);
            }

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "multipart/form-data;boundary=" + System.currentTimeMillis())
                    .POST(HttpRequest.BodyPublishers.ofByteArray(bos.toByteArray()))
                    .build();


            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (200 != res.statusCode()) {
                log.error("请求失败");
                return;
            }
            String body = res.body();
            log.info("结果:{}", body);


        } catch (Exception e) {
            log.error("异常", e);
        }
    }

}