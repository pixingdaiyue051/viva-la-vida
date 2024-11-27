package com.tequeno.vivaboot.config.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tequeno.constants.HtJmsConstant;
import com.tequeno.dto.HtJmsRocketModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RocketProducer {

    @Resource
    private TransactionMQProducer producer;

    @Resource
    private ThreadPoolTaskExecutor pool;

    /**
     * 发送单条消息
     * setDelayTimeLevel  [1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h]
     * setDelayTimeMs  毫秒
     * setDelayTimeSec 秒
     * setDeliverTimeMs    自定义时间
     *
     * @param model
     */
    public void send(HtJmsRocketModel model) {
        pool.execute(() -> {
            try {
                model.setTimestamp(System.currentTimeMillis());
                byte[] body = JSON.toJSONString(model).getBytes(RemotingHelper.DEFAULT_CHARSET);
                Message msg = new Message(HtJmsConstant.ROCKET_R_TOPIC, body);
                msg.setTags(model.getTag());
                if (StringUtils.isNotEmpty(model.getKey())) {
                    msg.setKeys(model.getKey());
                }
                if (null != model.getDelay()) {
//                    msg.setDeliverTimeMs(model.getTimestamp() + model.getDelay());
                    msg.setDelayTimeMs(model.getDelay());
                }
                producer.send(msg);
            } catch (Exception e) {
                log.error("rocket发送消息[{}]失败", model.getCode(), e);
            }
        });
    }


    /**
     * 发送多条消息 不能发送延迟消息
     *
     * @param modelList
     */
    public void send(List<HtJmsRocketModel> modelList) {
        pool.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                List<Message> msgList = new ArrayList<>(modelList.size());
                for (HtJmsRocketModel model : modelList) {
                    model.setTimestamp(timestamp);
                    byte[] body = JSON.toJSONString(model).getBytes(RemotingHelper.DEFAULT_CHARSET);
                    Message msg = new Message(HtJmsConstant.ROCKET_R_TOPIC, body);
                    msg.setTags(model.getTag());
                    if (StringUtils.isNotEmpty(model.getKey())) {
                        msg.setKeys(model.getKey());
                    }
                    msgList.add(msg);
                }
                producer.send(msgList);
            } catch (Exception e) {
                log.error("rocket发送多条消息失败", e);
            }
        });
    }

    public void sendTransaction(HtJmsRocketModel model) {
        pool.execute(() -> {
            try {
                model.setTimestamp(System.currentTimeMillis());
                byte[] body = JSON.toJSONString(model).getBytes(RemotingHelper.DEFAULT_CHARSET);
                Message msg = new Message(HtJmsConstant.ROCKET_R_TOPIC, body);
                msg.setTags(model.getTag());
                if (StringUtils.isNotEmpty(model.getKey())) {
                    msg.setKeys(model.getKey());
                }
                producer.sendMessageInTransaction(msg, model.getTimestamp());
            } catch (Exception e) {
                log.error("rocket发送事务消息[{}]失败", model.getCode(), e);
            }
        });
    }

}