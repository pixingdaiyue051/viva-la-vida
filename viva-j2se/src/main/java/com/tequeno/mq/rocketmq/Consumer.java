//package com.tequeno.mq.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.message.MessageExt;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 消息接收消费
// *
// * @author : hexk
// * @date : 2019-11-20 09:50
// **/
//public class Consumer {
//
//    public static void main(String[] args) {
//        try {
//            consume(RocketmqConstant.INSTANCE_NAME_B, RocketmqConstant.DEFAULT_TAG_B);
//            consume(RocketmqConstant.INSTANCE_NAME_A, RocketmqConstant.DEFAULT_TAG_A);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void consume(String instanceName, String tag) {
//        try {
//            // Instantiate with specified consumer group name.
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketmqConstant.DEFAULT_GROUP);
//
//            // Specify name server addresses.
//            consumer.setNamesrvAddr(RocketmqConstant.DEFAULT_NAMESRV_ADDR);
//
//            // Subscribe one more more topics to consume.
//            consumer.subscribe(RocketmqConstant.DEFAULT_TOPIC, tag);
//            consumer.setInstanceName(instanceName);
//            // Register callback to execute on arrival of messages fetched from brokers.
//            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
//                MessageExt messageExt = msg.get(0);
//                String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
//                long bornTimestamp = messageExt.getBornTimestamp();
//                long storeTimestamp = messageExt.getStoreTimestamp();
//                String topic = messageExt.getTopic();
//                String tags = messageExt.getTags();
//                System.out.printf("%s Receive New Messages:%s%n", Thread.currentThread().getName(), msg);
//                System.out.printf("topic:%s,tag:%s,body:%s,bornTimestamp:%s,storeTimestamp:%s,delay:%s%n", topic, tags, body, bornTimestamp, storeTimestamp, storeTimestamp - bornTimestamp);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            });
//
//            //Launch the consumer instance.
//            consumer.start();
//
//            System.out.printf("Consumer Started.%n");
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//    }
//}