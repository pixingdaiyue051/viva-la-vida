package com.tequeno.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 每一个线程都自己有用一个pipeline decoder encoder handler完整的处理链
 * 不需要考虑多线程数据共享 资源竞争问题
 */
public class MyHttpServerDecoder extends MessageToMessageDecoder<HttpObject> {

    private final static String PATH = "/data/upload"; // 服务器保存文件的路径
    private final static String FILE_SUFFIX = "file-suffix"; // 文件后缀
    private final static String FILE_SIZE = "file-size"; // 文件大小
    private final static HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE); // MAXSIZE 表示不限制读取的字节数 都以文件的形式读取

    private HttpPostRequestDecoder decoder; // 解析post请求数据(包括multipart数据)
    private String fileSuffix;

    private long start;
    private long end;

    @Override
    protected void decode(ChannelHandlerContext ctx, HttpObject httpObject, List<Object> out) throws Exception {
//        System.out.println("消息类型:" + httpObject.getClass());
//        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
//        System.out.println("channel id:" + ctx.channel().id().asLongText());
//        System.out.println("pipeline hashcode:" + ctx.pipeline().hashCode());
//        System.out.println("当前线程:" + Thread.currentThread().getName());
//        System.out.println("****************************");
        // 解码得到http请求
        if (httpObject instanceof HttpRequest) {
            start = System.currentTimeMillis();
            HttpRequest request = (HttpRequest) httpObject;
            String uri = request.uri();
            System.out.println("请求uri" + uri);
            // 不处理网页图标请求 只有使用浏览器访问时会发送该请求
            if ("/favicon.ico".equals(uri)) {
                System.out.println("暂无网页图标");
                return;
            }
            // 即将上传文件 先打开文件通道
            if ("/upload".equals(uri)) {
                HttpHeaders headers = request.headers();

                fileSuffix = headers.get(FILE_SUFFIX);

                // TODO 对文件格式和大小做限制
//                Integer fileSize = headers.getInt(FILE_SIZE);
//                Integer contentLength = headers.getInt(HttpHeaderNames.CONTENT_LENGTH);

                decoder = new HttpPostRequestDecoder(FACTORY, request);
                return;
            }
            return;
        }

        // 解码得到请求体
        if (httpObject instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) httpObject;

            // 传输文件
            if (null != decoder) {
                decoder.offer(httpContent);
                // 最后一个数据块 一定要关闭decoder
                if (httpObject instanceof LastHttpContent) {
                    writeHttpContent();
                    decoder.destroy();
                    decoder = null;
                    ctx.writeAndFlush(LocalDateTime.now().toString());
                    end = System.currentTimeMillis();
                    System.out.println("自解码耗时:" + (end - start) + "ms");
                }
            } else {
                // 普通的请求
                ByteBuf in = httpContent.content();
                int len = in.readableBytes();
                if (len > 0) {
                    byte[] bytes = new byte[len];
                    in.readBytes(bytes);
                    out.add(new String(bytes, CharsetUtil.UTF_8));
                } else {
                    ctx.writeAndFlush(LocalDateTime.now().toString());
                }
                end = System.currentTimeMillis();
                System.out.println("自解码耗时:" + (end - start) + "ms");
            }
        }
    }

    private void writeHttpContent() {
        System.out.println("上传中...");
        try (FileChannel fileOutChannel = FileChannel.open(Paths.get(PATH, System.currentTimeMillis() + fileSuffix),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            List<InterfaceHttpData> httpDataList = decoder.getBodyHttpDatas();
            httpDataList.stream()
                    .filter(data -> InterfaceHttpData.HttpDataType.FileUpload.equals(data.getHttpDataType()))
                    .map(data -> (FileUpload) data)
                    .forEach(fileUpload -> {
                        try (FileChannel fileInChannel = new RandomAccessFile(fileUpload.getFile(), "r").getChannel()) {
                            System.out.println("读取数据块...");
                            long read, total = 0;
                            while ((read = fileInChannel.transferTo(total, fileInChannel.size(), fileOutChannel)) > 0) {
                                System.out.println("已传输..." + read);
                                total += read;
                            }
                            System.out.println("共传输..." + + total);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    // 错误的使用实例 不知道为什么不可用
//    private final static String PATH = "/data/upload/pic";
//    private final static String FILE_NAME = "file-name";
//    private final static String FILE_SIZE = "file-size";
//    private final static String CONTENT_LENGTH = "content-length";
//    private final static int FINED_FORM_END_LENGTH = 42;
//
//    private FileChannel fileChannel;
//    private int discardLength;
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, HttpObject httpObject, List<Object> out) throws Exception {
//        System.out.println("消息类型:" + httpObject.getClass());
//        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
//        System.out.println("channel id:" + ctx.channel().id().asLongText());
//        System.out.println("pipeline hashcode:" + ctx.pipeline().hashCode());
//        System.out.println("当前线程:" + Thread.currentThread().getName());
//        System.out.println("****************************");
//        // 解码得到http请求
//        if (httpObject instanceof HttpRequest) {
//            HttpRequest request = (HttpRequest) httpObject;
//            String uri = request.uri();
//            // 不处理网页图标请求 只有使用浏览器访问时会发送该请求
//            if ("/favicon.ico".equals(uri)) {
//                System.out.println("暂无网页图标");
//                return;
//            }
//
//            System.out.println("****************************");
//            request.headers().forEach(h -> {
//                System.out.print("key:" + h.getKey());
//                System.out.println("------value:" + h.getValue());
//            });
//            System.out.println("****************************");
//
//            // 即将上传文件 先打开文件通道
//            if (uri.startsWith("/upload")) {
//                HttpHeaders headers = request.headers();
//                // 文件名
//                String filename = request.headers().get(FILE_NAME);
//                // 文件后缀
//                String fileSuffix = filename.substring(filename.lastIndexOf("."));
//                // 消息体总长度
//                Integer contentLength = headers.getInt(CONTENT_LENGTH);
//                // 文件大小
//                Integer fileSize = headers.getInt(FILE_SIZE);
//                // 需要丢弃的字节数
//                discardLength = contentLength - fileSize - FINED_FORM_END_LENGTH;
//                // 开启文件通道
//                fileChannel = FileChannel.open(Paths.get(PATH, System.currentTimeMillis() + fileSuffix),
//                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//                return;
//            }
//            return;
//        }
//
//        // 解码得到请求体
//        if (httpObject instanceof HttpContent) {
//            HttpContent httpContent = (HttpContent) httpObject;
//            ByteBuf in = httpContent.content();
//
//            // 传输文件
//            if (null != fileChannel) {
//                // 先丢弃发送的数据分隔符
//                if (discardLength > 0) {
//                    in.skipBytes(discardLength);
//                    discardLength = 0;
//                }
//
//                // 最后一个数据块
//                if (httpObject instanceof LastHttpContent) {
//                    int len = in.readableBytes() - FINED_FORM_END_LENGTH;
//                    byte[] bytes = new byte[len];
//                    in.readBytes(bytes);
//                    fileChannel.write(ByteBuffer.wrap(bytes));
//                    fileChannel.close();
//                    fileChannel = null;
//                    ctx.writeAndFlush(LocalDateTime.now().toString());
//                } else {
//                    int len = in.readableBytes();
//                    byte[] bytes = new byte[len];
//                    in.readBytes(bytes);
//                    fileChannel.write(ByteBuffer.wrap(bytes));
//                }
//                return;
//            }
//
//            // 普通的请求
//            int len = in.readableBytes();
//            if (len > 0) {
//                byte[] bytes = new byte[len];
//                in.readBytes(bytes);
//                out.add(new String(bytes, CharsetUtil.UTF_8));
//            } else {
//                ctx.writeAndFlush(LocalDateTime.now().toString());
//            }
//        }
//
//    }
}