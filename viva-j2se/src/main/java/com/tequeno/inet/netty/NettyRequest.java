package com.tequeno.inet.netty;

import java.util.List;

public class NettyRequest {

    private String code;

    private String msg;

    private String key;

    private String value;

    private List<NettyResponse> list;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<NettyResponse> getList() {
        return list;
    }

    public void setList(List<NettyResponse> list) {
        this.list = list;
    }
}
