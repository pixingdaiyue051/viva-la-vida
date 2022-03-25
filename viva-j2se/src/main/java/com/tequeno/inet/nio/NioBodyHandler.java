package com.tequeno.inet.nio;

public class NioBodyHandler {

    public static NioBodyDto wrap(NioMsgCodeEnum codeEnum, String tid) {
        NioBodyDto dto = new NioBodyDto();
        dto.setTid(tid);
        dto.setCode(codeEnum.getCode());
        dto.setMsg(codeEnum.getDesc());
        dto.setData("");
        return dto;
    }

    public static NioBodyDto wrap(String code, String msg, String tid) {
        NioBodyDto dto = new NioBodyDto();
        dto.setTid(tid);
        dto.setCode(code);
        dto.setMsg(msg);
        dto.setData("");
        return dto;
    }
}
