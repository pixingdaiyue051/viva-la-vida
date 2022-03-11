package com.tequeno.inet.nioconst;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.InetUtil;

public class NioHeadHandler {

    public final static int FIXED_LENGTH = 82;
    private final static int HEAD_LENGTH = 555;
    private final static int TAIL_LENGTH = 666;

    public static String head(String tid) {
        return complete(wrap(HEAD_LENGTH, NioMsgTypeEnum.HEAD.getType(), tid));
    }

    public static String tail(String tid) {
        return complete(wrap(TAIL_LENGTH, NioMsgTypeEnum.TAIL.getType(), tid));
    }

    public static String text(int length, String tid) {
        return complete(wrap(length, NioMsgTypeEnum.TEXT.getType(), tid));
    }

    public static String pic(int length, String tid) {
        return complete(wrap(length, NioMsgTypeEnum.PIC.getType(), tid));
    }

    private static NioHeadDto wrap(int length, String type, String tid) {
        NioHeadDto dto = new NioHeadDto();
        dto.setLength(length);
        dto.setType(type);
        dto.setTid(tid);
        dto.setOffset("");
        return dto;
    }

    private static String complete(NioHeadDto dto) {
        String json = JSON.toJSONString(dto);
        int offset = FIXED_LENGTH - json.length();
        if (offset > 0) {
            dto.setOffset(InetUtil.random(offset));
            json = JSON.toJSONString(dto);
        }
        return json;
    }
}
