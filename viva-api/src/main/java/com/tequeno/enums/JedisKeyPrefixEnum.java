package com.tequeno.enums;

public enum JedisKeyPrefixEnum {
    USER("user", "用户模块"),

    SESSION("session", "session管理"),

    HTEST("htest", "编程范例测试用hash key"),
    TEST("test", "编程范例测试用普通key"),

    HDICT("hdict", "数据字典hash"),
    HUSER_PASSWORD("hpassword", "用户密码模块"),
    HUSER_RES("huserres", "用户权限"),
    HROLE("hrole", "角色模块"),
    HRES("hres", "权限模块"),

    SIGN("sign", "用户登录签名"),
    OTP("otp", "验证码"),

    LOCK("lock", "分布式锁"),
    SEQ("seq", "流水号"),
    QUEUE("queue", "延迟队列"),
    TOPIC("topic", "主题订阅发布"),
    REMOTE("remote", "服务注册发现"),
    RATE("rate", "限流器"),
    BLOOM("bloom", "布隆过滤器"),

    ;
    private final String prefix;
    private final String msg;

    JedisKeyPrefixEnum(String prefix, String msg) {
        this.prefix = prefix;
        this.msg = msg;
    }

    public String assemblyKey(String key) {
        return String.format("%s:%s", prefix, key);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getMsg() {
        return msg;
    }
}