package com.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * one-way发送消息
 *
 * @author : hexk
 * @date : 2019-11-20 09:50
 **/
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConstant.DEFAULT_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketmqConstant.DEFAULT_NAMESRV_ADDR);
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendFailed(RocketmqConstant.SEND_SYNC_FAILED_RETRY);
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(RocketmqConstant.DEFAULT_TOPIC,
                    RocketmqConstant.DEFAULT_TAG,
                    ("Hello world" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}