package com.tequeno.netty.ws;

import com.alibaba.fastjson.JSON;
import com.tequeno.netty.NettyConstant;
import com.tequeno.netty.NettyRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketNativeListener implements WebSocket.Listener {

    private final static Logger log = LoggerFactory.getLogger(WebSocketNativeListener.class);

    private final ScheduledExecutorService pool;

    public WebSocketNativeListener(ScheduledExecutorService pool) {
        this.pool = pool;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        log.info("onOpen");
        pool.scheduleAtFixedRate(() -> webSocket.sendText(JSON.toJSONString(NettyRequestHandler.heartBeat()), true), 0, NettyConstant.WRITER_IDLE_TIME, TimeUnit.SECONDS);
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        log.info("onText {},是否为最终包{}", data, last);
        webSocket.request(1);
        return null;
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        log.info("onBinary {},是否为最终包{}", data, last);
        return null;
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        log.info("onPing {}", message);
        return null;
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        log.info("onPong {}", message);
        return null;
    }

}
