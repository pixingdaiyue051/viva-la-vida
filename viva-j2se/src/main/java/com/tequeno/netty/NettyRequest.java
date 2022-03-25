package com.tequeno.netty;

public class NettyRequest {

    private String code;

    private String msg;

    private String key;

    private Object value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NettyRequest{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}