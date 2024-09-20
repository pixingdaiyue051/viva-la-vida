package com.tequeno.netty.http;

import com.alibaba.fastjson.JSON;
import com.tequeno.netty.NettyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MyHttpServerEncoder extends MessageToMessageEncoder<NettyResponse> {

    private final static Logger log = LoggerFactory.getLogger(MyHttpServerEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyResponse msg, List<Object> out) throws Exception {

        log.info("MyHttpServerEncoder");

        ByteBuf content = Unpooled.copiedBuffer(JSON.toJSONString(msg), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers()
                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*")
//                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE")
//                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")

                .set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8")
//                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8")
                .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        out.add(response);
    }
}
