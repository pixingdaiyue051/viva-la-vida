package com.tequeno.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class MessageProducer {

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
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActivemqConstant.ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标
        Destination destination = null;
        if (isTopic) {
            destination = session.createTopic(ActivemqConstant.TOPIC_NAME);
        } else {
            destination = session.createQueue(ActivemqConstant.QUEUE_NAME);
        }
        //创建一个生产者
        javax.jms.MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 10秒延迟发送消息
        String msg = "我发送message-delay";
        Message message = session.createTextMessage(msg);
//        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 100 * 1000L);
//        producer.send(message);
//        System.out.println(message);
        // 间隔5秒发送消息，总共重复发送10次
//        msg = "我发送message-repeat";
//        message = session.createTextMessage(msg);
//        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1 * 60 * 1000L);
//        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1 * 60 * 1000L);
//        message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 100);
//        producer.send(message);
//        System.out.println(message);
        // 使用cron表达式发送消息,非quartz表达式,使用unix schedule表达式
//      minute（0 - 59）
//      hour（0 - 23）
//      day of month（1 - 31） 几号
//      month（1 - 12）
//      day of week（0 - 7，星期几，星期日=0或7）
        msg = "我发送message-cron";
        message = session.createTextMessage(msg);
        message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "*/5 14-17 * * *");
        producer.send(message);
        System.out.println(message);
        //关闭连接
        session.close();
        connection.close();
    }
}