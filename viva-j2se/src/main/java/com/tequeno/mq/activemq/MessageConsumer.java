//package com.tequeno.mq.activemq;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
//public class MessageConsumer {
//
//    public static void main(String[] args) {
//        try {
////            recieve(false);
//            recieve(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void recieve(boolean isTopic) throws JMSException {
//        //创建连接工厂
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActivemqConstant.ACTIVEMQ_URL);
//        //创建连接
//        Connection connection = activeMQConnectionFactory.createConnection();
//        //打开连接
//        connection.start();
//        //创建会话
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //创建队列目标
//        Destination destination = null;
//        if (isTopic) {
//            destination = session.createTopic(ActivemqConstant.TOPIC_NAME);
//        } else {
//            destination = session.createQueue(ActivemqConstant.QUEUE_NAME);
//        }
//        //创建消费者
//        javax.jms.MessageConsumer consumer = session.createConsumer(destination);
//        //创建消费的监听
//        consumer.setMessageListener(message -> {
//            TextMessage textMessage = (TextMessage) message;
//            try {
//                System.out.println("获取消息：" + textMessage.getText());
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
//}