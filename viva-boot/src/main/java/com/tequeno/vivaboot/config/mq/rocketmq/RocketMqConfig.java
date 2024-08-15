package com.tequeno.vivaboot.config.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 设置环境变量 ROCKETMQ_HOME E:\rawsoft\rocketmq-5.1.2
 * 启动nameserver mqnamesrv
 * 启动broker并绑定到nameserver     mqbroker -n 127.0.0.1:9876
 * 查询topic列表    mqadmin topicList -n 192.168.3.173:9876
 * 创建topic      mqadmin updateTopic -b 127.0.0.1:10911 -t r_topic -a +message.type=DELAY
 */
@Configuration
public class RocketMqConfig {

    private final static Logger logger = LoggerFactory.getLogger(RocketMqConfig.class);

    @Value("${rocketmq.adder}")
    private String adder;

    @Value("${rocketmq.group}")
    private String group;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.producer.instanceName}")
    private String producerInstanceName;

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Value("${rocketmq.producer.retryTimesWhenSendAsyncFailed}")
    private Integer retryTimesWhenSendAsyncFailed;

    @Value("${rocketmq.producer.vipChannelEnabled}")
    private Boolean vipChannelEnabled;

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    @Value("${rocketmq.consumer.instanceName}")
    private String consumerInstanceName;

    @Value("${rocketmq.consumer.subExpression}")
    private String subExpression;

    @Value("${rocketmq.consumer.consumeTimeout}")
    private Long consumeTimeout;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;

    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

    @Bean(destroyMethod = "shutdown")
    public DefaultMQProducer defaultMQProducer() {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr(adder);
            producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
            producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);
            producer.setVipChannelEnabled(vipChannelEnabled);
            producer.setInstanceName(producerInstanceName);
            producer.setSendMsgTimeout(sendMsgTimeout);
            producer.setMaxMessageSize(maxMessageSize);
//            producer.start();
            return producer;
        } catch (Exception e) {
            logger.error("rocketmq producer[{}]启动失败", producerInstanceName, e);
            return null;
        }
    }

    @Resource
    private RocketConsumer rocketConsumer;

    @Bean(destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
            consumer.setNamesrvAddr(adder);
            consumer.setInstanceName(consumerInstanceName);
            consumer.subscribe(topic, subExpression);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setConsumeTimeout(consumeTimeout);
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            consumer.registerMessageListener(rocketConsumer::consumeConcurrently);
//            consumer.start();
            return consumer;
        } catch (Exception e) {
            logger.error("rocketmq consumer[{}]启动失败", consumerInstanceName, e);
            return null;
        }
    }
}