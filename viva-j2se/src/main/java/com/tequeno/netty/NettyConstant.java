package com.tequeno.netty;

import io.netty.util.AttributeKey;

/**
 * @author PC
 * @since 2020/12/4 15:39
 */
public final class NettyConstant {

    public final static String HOST = "127.0.0.1";

    public final static int PORT = 7132;

    /**
     * 写空闲间隔
     */
    public final static int WRITER_IDLE_TIME = 30;

    /**
     * 读空闲间隔
     */
    public final static int READER_IDLE_TIME = 60;

    /**
     * 最大空闲次数
     */
    public final static int MAX_IDLE_TIMES = 5;

    /**
     * 检测到已经达到 最大空闲次数 延时断开连接
     */
    public final static int LATER_TO_CLOSE = 5;

    public final static AttributeKey<Object> ATTR_READ_IDLE = AttributeKey.valueOf(NettyKeyEnum.READ_IDLE.getKey());

    public final static AttributeKey<Object> ATTR_CHANNEL_INACTIVE = AttributeKey.valueOf(NettyKeyEnum.CHANNEL_INACTIVE.getKey());

    public static final String BREAK_OUT = "110";
}