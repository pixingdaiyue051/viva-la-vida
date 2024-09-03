package com.tequeno.constants;

public class HtJmsConstant {

    //// rocketmq
    public static final String ROCKET_R_TOPIC = "r_topic";

    /**
     * * 表示订阅当前topic下所有的tag,配置特殊字符需要使用'(英文半角单引号)
     * || 表示同时订阅当前topic下多个tag 比如 r_tag_a || r_tag_b
     */
    public static final String ROCKET_TAG_ALL = "*";

    public static final String ROCKET_TAG_A = "r_tag_a";

    public static final String ROCKET_TAG_B = "r_tag_b";

    //// rabbitmq
    public static final String EXCHANGE_NAME = "r_exchange";

    public static final String EXCHANGE_TYPE = "direct";

    public static final String QUEUE_NAME = "r_queue";

    public static final String ROUTING_KEY = "r_routing";

    //// activemq
    /**
     * jms主题订阅模式容器bean名字
     */
    public final static String TOPIC_CONTAINER_FACTORY = "jmsListenerContainerTopic";

    /**
     * jms队列模式容器bean名字
     */
    public final static String QUEUE_CONTAINER_FACTORY = "jmsListenerContainerQueue";

    /**
     * 队列名字
     */
    public final static String QUEUE_SCHEDULED_NAME = "scheduledQueue";
    public final static String QUEUE_SIMPLE_NAME = "simpleQueue";

    /**
     * 主题名字
     */
    public final static String TOPIC_SCHEDULED_NAME = "scheduledTopic";
    public final static String TOPIC_SIMPLE_NAME = "simpleTopic";
}
