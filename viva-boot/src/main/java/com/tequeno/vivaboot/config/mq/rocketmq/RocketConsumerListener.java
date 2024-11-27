package com.tequeno.vivaboot.config.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tequeno.dto.HtJmsRocketModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class RocketConsumerListener implements MessageListenerConcurrently {

    /**
     * 并发获得队列中的消息
     *
     * @param msgList
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        int failed = 0;
        for (MessageExt messageExt : msgList) {
            try {
                String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                HtJmsRocketModel model = JSON.parseObject(body, HtJmsRocketModel.class);

                long bornTimestamp = messageExt.getBornTimestamp(); // bornTimestamp 消息发送时间
                long storeTimestamp = messageExt.getStoreTimestamp(); // storeTimestamp   消息在broker存储时间 可代表消费时间
                String topic = messageExt.getTopic();
                String tags = messageExt.getTags();

                log.info("topic:[{}],tag:[{}],body:[{}]", topic, tags, body);
                log.info("storeTimestamp:[{}],bornTimestamp:[{}],delay:[{}]", storeTimestamp, bornTimestamp, storeTimestamp - bornTimestamp);

                if (null != model.getDelay()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    log.info("rocket 当前时间[{}],发送时间[{}],相差[{}],预设延迟[{}]", currentTimeMillis, model.getTimestamp(), currentTimeMillis - model.getTimestamp(), model.getDelay());
                }
            } catch (Exception e) {
                log.error("rocket 消息处理异常, msgId:[{}]", messageExt.getMsgId(), e);
                failed++;
            }
        }
        if (failed > 0) {
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
