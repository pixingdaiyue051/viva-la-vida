package com.tequeno.inet.nio;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Desription:
 * @Author: hexk
 */
public class NioSocketTest {

    public static void main(String[] args) throws IOException {
        NioSocketTest nioSocketTest = new NioSocketTest();
        nioSocketTest.testConnect();
    }

    private void testConnect() {
        try (SocketChannel socketChannel = SocketChannel.open();
//             FileChannel fileChannel = FileChannel.open(Paths.get("/data/pic/微信图片_20210906142044.jpg"), StandardOpenOption.READ)
//             FileChannel fileChannel = FileChannel.open(Paths.get("/data/doc/3.txt"), StandardOpenOption.READ)
        ) {
            boolean connected = socketChannel.connect(new InetSocketAddress("127.0.0.1", 7613));
            if (!connected) {
                return;
            }

            // 模拟发送
            String tid = NioUtil.uuid();

            NioBodyDto dto = NioBodyHandler.wrap(NioMsgCodeEnum.CODE_1, tid);
            byte[] src = JSON.toJSONString(dto).getBytes(StandardCharsets.UTF_8);
            ByteBuffer bodyBuf = ByteBuffer.allocate(src.length);
            bodyBuf.put(src);

            ByteBuffer headBuf = ByteBuffer.allocate(NioHeadHandler.FIXED_LENGTH);
            String head = NioHeadHandler.text(src.length, tid);
            headBuf.put(head.getBytes(StandardCharsets.UTF_8));

            ByteBuffer[] byteBuffers = {headBuf, bodyBuf};
            headBuf.flip();
            bodyBuf.flip();
            socketChannel.write(byteBuffers);
            headBuf.clear();
            bodyBuf.clear();

            // 模拟接收
//            ByteBuffer readBuf = ByteBuffer.allocate(1024);
//            socketChannel.read(readBuf);
//            readBuf.flip();
//            byte[] bytes = new byte[1024];
//            readBuf.get(bytes);
//            System.out.println(new String(bytes));
//            readBuf.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}