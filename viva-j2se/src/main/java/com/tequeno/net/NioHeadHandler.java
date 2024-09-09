package com.tequeno.net;

import com.alibaba.fastjson.JSON;

import java.util.Random;

public class NioHeadHandler {

    public final static int FIXED_LENGTH = 82;
    private final static int HEAD_LENGTH = 555;
    private final static int TAIL_LENGTH = 666;

    public static String head(String tid) {
        return wrap(HEAD_LENGTH, NioMsgTypeEnum.HEAD.getType(), tid);
    }

    public static String tail(String tid) {
        return wrap(TAIL_LENGTH, NioMsgTypeEnum.TAIL.getType(), tid);
    }

    public static String text(int length, String tid) {
        return wrap(length, NioMsgTypeEnum.TEXT.getType(), tid);
    }

    public static String pic(int length, String tid) {
        return wrap(length, NioMsgTypeEnum.PIC.getType(), tid);
    }

    private static String wrap(int length, String type, String tid) {
        NioHeadDto dto = new NioHeadDto();
        dto.setLength(length);
        dto.setType(type);
        dto.setTid(tid);
        dto.setOffset("");

        String json = JSON.toJSONString(dto);
        int offset = FIXED_LENGTH - json.length();
        if (offset > 0) {
            dto.setOffset(random(offset));
            json = JSON.toJSONString(dto);
        }
        return json;
    }

    private static final String[] NUM_POOL = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    private static String random(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(NUM_POOL[random.nextInt(NUM_POOL.length)]);
        }
        return builder.toString();
    }

}
