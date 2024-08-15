package com.tequeno.enums;

public enum HtUserErrorEnum implements HtErrorInterface {
    USER_NOT_EXIST("1001", "用户不存在"),
    USERNAME_NOT_EMPTY("1002", "用户名不为空"),
    PASSWORD_NOT_EMPTY("1003", "密码不为空"),
    PHONE_NOT_MATCHED("1004", "手机号码不正确"),
    MAIL_NOT_MATCHED("1005", "邮箱不正确"),
    ID_NOT_MATCHED("1006", "id不为空"),
    USERNAME_CANNOT_MODIFY("1007", "用户名不允许修改"),
    TRUENAME_NOT_EMPTY("1008", "真实姓名不为空"),

    PASSWORD_ENCODE_FAILED("1100", "密码加密失败"),
    BIND_ERROR_1("1101", "不能同时绑定手机和邮箱"),
    BIND_ERROR_2("1102", "手机或邮箱不为空"),

    LOGIN_SUCCESSED("1200", "登录成功"),
    NOT_LOGINED("1201", "未登录"),
    LOGINED_AREADY("1202", "已登录"),
    LOGIN_FAILED("1203", "登录失败"),
    USERNAME_OR_PASSWORD_ERROR("1204", "用户名或密码错误"),
    USER_LOCKED("1204", "账号被锁定"),
    USER_DISABLED("1205", "账号被禁用"),
    LOGOUT("1206", "已退出"),
    CAPTCHA_ERROR("1207", "验证码输入有误"),
    CAPTCHA_GEN_ERROR("1208", "图片验证码生成失败"),

    NOT_PERMITTED("2000", null),
    ;
    private final String code;
    private final String msg;

    HtUserErrorEnum(String code, String msg) {
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