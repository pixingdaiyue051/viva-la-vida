package com.tequeno.net;

import com.alibaba.fastjson.JSON;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientHandler {

    private final static Logger log = LoggerFactory.getLogger(HttpClientHandler.class);

    public void url(String url) {
        try {
            URL u = new URL(url);

            try (InputStream is = u.openStream();
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] b = new byte[1024];
                int len;
                while ((len = is.read(b)) > 0) {
                    bos.write(b, 0, len);
                }

                String out = bos.toString();
                log.info("结果:{}", out);
            }

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

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

    public void postJson(String url, Map<String, String> map) {
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

    public void postFormUrlencoded(String url, Map<String, String> map) {
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

    public void postMultiFormData(String url, Map<String, String> map, Map<String, File> fileMap) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
//            String boundary = String.valueOf(System.currentTimeMillis());
//            httpPost.setHeader("Content_Type", "multipart/form-data;charset=UTF-8;boundary=" + boundary);
//            httpPost.setHeader("Accept-Encoding", "UTF-8");

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                    .setCharset(StandardCharsets.UTF_8)
//                    .setBoundary(boundary)
                    ;
            if (null != map && !map.isEmpty()) {
                map.forEach((k, v) -> entityBuilder.addTextBody(k, v, ContentType.TEXT_XML));
            }
            if (null != fileMap && !fileMap.isEmpty()) {
                fileMap.forEach(entityBuilder::addBinaryBody);
            }
            httpPost.setEntity(entityBuilder.build());

            String executed = httpclient.execute(httpPost, res -> {
                if (HttpStatus.SC_OK != res.getCode()) {
                    return "";
                }
                String s = EntityUtils.toString(res.getEntity(), StandardCharsets.UTF_8);
                if (null == s || s.isEmpty()) {
                    return "";
                }
                return s;
            });
            log.info("结果:{}", executed);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}