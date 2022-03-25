package com.tequeno.netty.ws;

import com.alibaba.fastjson.JSON;
import com.tequeno.netty.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class WebSocketServerEncoder extends MessageToMessageEncoder<NettyResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyResponse response, List<Object> out) throws Exception {
        System.out.println("server-----encode");
        String msg = JSON.toJSONString(response);
        out.add(new TextWebSocketFrame(msg));
    }
}
