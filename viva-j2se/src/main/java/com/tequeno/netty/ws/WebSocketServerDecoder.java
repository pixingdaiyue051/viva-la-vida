package com.tequeno.netty.ws;

import com.alibaba.fastjson.JSON;
import com.tequeno.netty.NettyCodeEnum;
import com.tequeno.netty.NettyRequest;
import com.tequeno.netty.NettyResponseHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WebSocketServerDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    private final static String PATH = "/data/upload";

    private FileChannel fileChannel;

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        System.out.println("server-----decode" + frame.isFinalFragment());
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            String msg = textFrame.text();
            System.out.println(msg);
            NettyRequest request = JSON.parseObject(msg, NettyRequest.class);
            if (NettyCodeEnum.BIN_FILE_META.getCode().equals(request.getCode())) {
//                String filename = request.getMsg();
//                String fileSuffix = filename.substring(filename.lastIndexOf("."));
                String fileSuffix = request.getKey();
                fileChannel = FileChannel.open(Paths.get(PATH, System.currentTimeMillis() + fileSuffix),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                return;
            }
            out.add(request);
        } else if (frame instanceof BinaryWebSocketFrame) {
            binaryMsgHandler(ctx, frame);
        } else if (frame instanceof ContinuationWebSocketFrame) {
            binaryMsgHandler(ctx, frame);
        } else {
            System.out.println("不支持的类型");
            WebSocketServer.sendMsg(NettyResponseHandler.fail("不支持的类型"), ctx);
        }
    }

    private void binaryMsgHandler(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        ByteBuf in = frame.content();
        int len = in.readableBytes();
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        fileChannel.write(byteBuffer);
        if (frame.isFinalFragment()) {
            fileChannel.close();
            WebSocketServer.sendMsg(NettyResponseHandler.success("已上传"), ctx);
        }
    }
}
