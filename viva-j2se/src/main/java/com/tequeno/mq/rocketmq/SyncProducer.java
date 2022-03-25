package com.tequeno.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


/**
 * 同步发送消息
 *
 * @author : hexk
 * @date : 2019-11-20 09:45
 **/
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConstant.DEFAULT_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketmqConstant.DEFAULT_NAMESRV_ADDR);
        // Launch the instance.
        producer.setRetryTimesWhenSendFailed(RocketmqConstant.SEND_FAILED_RETRY);
        producer.setInstanceName(RocketmqConstant.INSTANCE_NAME_B);
        producer.start();
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(RocketmqConstant.DEFAULT_TOPIC,
                    RocketmqConstant.DEFAULT_TAG_B,
                    ("Hello world" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // default level
            // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//            msg.setDelayTimeLevel(2);
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}