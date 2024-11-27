package com.tequeno.enums;

public enum HtCommonErrorEnum implements HtErrorInterface {
    SUCCESS("0000", "成功"),
    FAIL("0001", "失败"),
    SYSTEM_ERROR("0003", "系统异常"),
    CUSTOM_ERROR("0009", ""),

    PARAMETER_NOT_EMPTY("0100", "未接收到请求参数"),
    PARAMETER_NOT_MATCHED("0101", "参数类型不匹配"),
    PARAMETER_NOT_VALID("0102", "参数不全"),
    OBJECT_NOT_FETCHED("0103", "未查询到指定数据"),
    OTP_NULL_OR_EXPIRED("0104", "未匹配到验证码或者验证码已过期"),
    WRONG_OTP("0105", "验证码错误"),
    OTP_NOT_EMPTY("0106", "验证码不为空"),
    LATER_TO_RETRY("0107", "请稍候再试"),
    SIGN_NOT_FOUND("0108", "签名不存在"),

    ;
    private final String code;
    private final String msg;

    HtCommonErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}