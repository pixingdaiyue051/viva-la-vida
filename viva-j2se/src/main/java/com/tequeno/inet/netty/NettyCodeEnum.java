package com.tequeno.inet.netty;


public enum NettyCodeEnum {

    SUCCESS("1000", "成功"),
    FAIL("1100", "失败"),
    EXCEPTION("1101", "异常"),

    HEART("2000", "心跳包"),
    SUB("2001", "订阅包"),
    BIZ("2002", "业务包"),
    ;
    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    NettyCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
