package com.tequeno.vivaboot.config.mq.rocketmq;

import com.tequeno.constants.HtJmsConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * 设置环境变量 ROCKETMQ_HOME E:\rawsoft\rocketmq-5.1.2
 * 启动nameserver mqnamesrv
 * 启动broker并绑定到nameserver     mqbroker -n 127.0.0.1:9876
 * 查询topic列表    mqadmin topicList -n 192.168.3.173:9876
 * 创建topic      mqadmin updateTopic -b 127.0.0.1:10911 -t r_topic -a +message.type=NORMAL
 */
@Configuration
public class RocketMqConfig {

    private final static Logger log = LoggerFactory.getLogger(RocketMqConfig.class);

    @Value("${rocketmq.adder}")
    private String adder;

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.producer.instance}")
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

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Value("${rocketmq.consumer.instance}")
    private String consumerInstanceName;

    @Value("${rocketmq.consumer.consumeTimeout}")
    private Long consumeTimeout;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;

    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

    @Resource
    private RocketProducerListener rocketProducerListener;

    @Resource
    private RocketConsumerListener rocketConsumerListener;

    @Bean(destroyMethod = "shutdown")
    public TransactionMQProducer transactionMQProducer() {
        try {
            TransactionMQProducer producer = new TransactionMQProducer(producerGroup);
            producer.setNamesrvAddr(adder);
            producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
            producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);
            producer.setVipChannelEnabled(vipChannelEnabled);
            producer.setInstanceName(producerInstanceName);
            producer.setSendMsgTimeout(sendMsgTimeout);
            producer.setMaxMessageSize(maxMessageSize);
            producer.setExecutorService(Executors.newCachedThreadPool());
            producer.setTransactionListener(rocketProducerListener);
//            producer.start();
//            log.info("rocketmq transaction producer[{}]启动成功", producerInstanceName);
            return producer;
        } catch (Exception e) {
            log.error("rocketmq transaction producer[{}]启动失败", producerInstanceName, e);
            return null;
        }
    }

    @Bean(destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(adder);
            consumer.setInstanceName(consumerInstanceName);
            consumer.subscribe(HtJmsConstant.ROCKET_R_TOPIC, HtJmsConstant.ROCKET_TAG_ALL);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setConsumeTimeout(consumeTimeout);
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            consumer.registerMessageListener(rocketConsumerListener);
//            consumer.start();
//            log.info("rocketmq consumer[{}]启动成功", consumerInstanceName);
            return consumer;
        } catch (Exception e) {
            log.error("rocketmq consumer[{}]启动失败", consumerInstanceName, e);
            return null;
        }
    }
}