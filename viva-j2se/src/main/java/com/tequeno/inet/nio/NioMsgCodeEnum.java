package com.tequeno.inet.nio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum NioMsgCodeEnum {

    CODE_1("1001", "[服务器]"),
    CODE_2("1002", "[客户端]"),
    CODE_3("1003", "校验"),
    CODE_4("1004", "退出"),
    ;

    String code;
    String desc;

    NioMsgCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String desc(String code) {
        return NioMsgCodeHolder.map.get(code);
    }

    private static class NioMsgCodeHolder {
        private final static Map<String, String> map = new HashMap<>(11);

        static {
            Arrays.stream(NioMsgCodeEnum.values()).forEach(item -> map.put(item.code, item.desc));
        }
    }

}
