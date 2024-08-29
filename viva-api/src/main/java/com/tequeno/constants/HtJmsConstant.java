package com.tequeno.constants;

public class HtJmsConstant {

    public static final String ROCKET_R_TOPIC = "r_topic";

    /**
     * * 表示订阅当前topic下所有的tag,配置特殊字符需要使用'(英文半角单引号)
     * || 表示同时订阅当前topic下多个tag 比如 r_tag_a || r_tag_b
     */
    public static final String ROCKET_TAG_ALL = "*";

    public static final String ROCKET_TAG_A = "r_tag_a";

    public static final String ROCKET_TAG_B = "r_tag_b";
}
