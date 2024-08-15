package com.tequeno.vivaboot.config.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tequeno.dto.HtJmsRocketModel;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RocketConsumer {

    private final static Logger logger = LoggerFactory.getLogger(RocketConsumer.class);

    /**
     * 并发获得队列中的消息
     *
     * @param msgList
     * @param context
     * @return
     */
    public ConsumeConcurrentlyStatus consumeConcurrently(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        msgList.forEach(this::consume);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 顺序获得队列中的消息
     *
     * @param msgList
     * @param context
     * @return
     */
    public ConsumeOrderlyStatus consumeOrderly(List<MessageExt> msgList, ConsumeOrderlyContext context) {
        msgList.forEach(this::consume);
        return ConsumeOrderlyStatus.SUCCESS;
    }

    private void consume(MessageExt messageExt) {
        String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        HtJmsRocketModel model = JSON.parseObject(body, HtJmsRocketModel.class);

        long bornTimestamp = messageExt.getBornTimestamp(); // bornTimestamp 消息发送时间
        long storeTimestamp = messageExt.getStoreTimestamp(); // storeTimestamp   消息在broker存储时间 可代表消费时间
        String topic = messageExt.getTopic();
        String tags = messageExt.getTags();
        long deliverTimeMs = messageExt.getDeliverTimeMs();

        logger.info("topic:[{}],tag:[{}],body:[{}]", topic, tags, body);
        logger.info("storeTimestamp:[{}],bornTimestamp:[{}],deliverTimeMs:[{}],delay:[{}]", storeTimestamp, bornTimestamp, deliverTimeMs, storeTimestamp - bornTimestamp);

        if (null != model.getDelay()) {
            long currentTimeMillis = System.currentTimeMillis();
            logger.info("rocket 当前时间[{}],发送时间[{}],相差[{}],预设延迟[{}]", currentTimeMillis, model.getTimestamp(), currentTimeMillis - model.getTimestamp(), model.getDelay());
        }

    }
}