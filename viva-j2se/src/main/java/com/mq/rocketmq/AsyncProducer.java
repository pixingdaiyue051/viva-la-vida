package com.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 异步发送消息
 *
 * @author : hexk
 * @date : 2019-11-20 09:48
 **/
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConstant.DEFAULT_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketmqConstant.DEFAULT_NAMESRV_ADDR);
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(RocketmqConstant.SEND_ASYNC_FAILED_RETRY);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(RocketmqConstant.DEFAULT_TOPIC,
                    RocketmqConstant.DEFAULT_TAG,
                    "OrderID188",
                    ("Hello world" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}