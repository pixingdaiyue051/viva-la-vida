package com.tequeno.net.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class UrlHandler {

    private final static Logger log = LoggerFactory.getLogger(UrlHandler.class);

    public void run(String url) {
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

}
