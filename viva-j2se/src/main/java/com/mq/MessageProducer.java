package com.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class MessageProducer {

    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";

    private static final String QUEUE_NAME = "queue.test";

    private static final String TOPIC_NAME = "topic.test";

    public static void main(String[] args) {
        try {
//            send(false);
            send(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(boolean isTopic) throws JMSException, InterruptedException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标
        Destination destination = null;
        if (isTopic) {
            destination = session.createTopic(TOPIC_NAME);
        } else {
            destination = session.createQueue(QUEUE_NAME);
        }
        //创建一个生产者
        javax.jms.MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 10秒延迟发送消息
        String msg = "我发送message-delay";
        Message message = session.createTextMessage(msg);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10 * 1000L);
        producer.send(message);
        System.out.println(message);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 间隔5秒发送消息，总共重复发送10次
        msg = "我发送message-repeat";
        message = session.createTextMessage(msg);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5 * 1000L);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 5 * 1000L);
        message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 10);
        producer.send(message);
        System.out.println(message);
        // 使用cron表达式发送消息
        msg = "我发送message-cron";
        message = session.createTextMessage(msg);
        message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");
        producer.send(message);
        System.out.println(message);
//        // 删除消息
//        Message request = session.createMessage();
//        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_REMOVE, message.getJMSMessageID());
//        producer.send(request);
//        System.out.println(message);
        //关闭连接
        session.close();
        connection.close();
    }
}