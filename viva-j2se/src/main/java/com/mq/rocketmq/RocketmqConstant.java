package com.mq.rocketmq;

/**
 * rocketmq基础信息常量
 *
 * @author : hexk
 * @date : 2019-11-20 09:58
 **/
public class RocketmqConstant {

    public static final String DEFAULT_GROUP = "r_group_test";

    public static final String DEFAULT_NAMESRV_ADDR = "localhost:9876";

    public static final String DEFAULT_TOPIC = "r_topic_test";

    public static final String DEFAULT_TAG = "r_tag_a";

    public static final int SEND_SYNC_FAILED_RETRY = 0;

    public static final int SEND_ASYNC_FAILED_RETRY = 0;
}