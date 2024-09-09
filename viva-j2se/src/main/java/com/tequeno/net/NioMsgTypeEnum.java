package com.tequeno.net;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum NioMsgTypeEnum {

    TEXT("1", "文本"),
    PIC("2", "图片"),
    AUD("3", "音频"),
    VOD("4", "视频"),

    HEAD("0", "消息头"),
    TAIL("9", "消息尾"),
    ;

    String type;
    String desc;

    NioMsgTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String desc(String type) {
        return NioMsgTypeHolder.map.get(type);
    }

    private static class NioMsgTypeHolder {
        private final static Map<String, String> map = new HashMap<>(11);

        static {
            Arrays.stream(NioMsgTypeEnum.values()).forEach(item -> map.put(item.type, item.desc));
        }
    }
}
