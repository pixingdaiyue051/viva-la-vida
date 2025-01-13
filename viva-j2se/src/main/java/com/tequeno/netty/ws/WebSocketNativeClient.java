package com.tequeno.netty.ws;

import com.tequeno.netty.NettyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * ws客户端
 */
public class WebSocketNativeClient {

    private final static Logger log = LoggerFactory.getLogger(WebSocketNativeClient.class);

    public void websocket(String host, int port) {
        try {
            ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

            pool.execute(() -> {
                String wsPath = String.format("ws://%s:%d", host, port);
                HttpClient.newHttpClient()
                        .newWebSocketBuilder()
                        .buildAsync(URI.create(wsPath), new WebSocketNativeListener(pool));
            });

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public static void main(String[] args) {
        WebSocketNativeClient app = new WebSocketNativeClient();
        app.websocket(NettyConstant.HOST, NettyConstant.PORT);
    }
}
