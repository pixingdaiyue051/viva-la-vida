package com.tequeno.vivaboot.config.mq.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RocketProducerListener implements TransactionListener {

    private final static Logger log = LoggerFactory.getLogger(RocketProducerListener.class);

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            log.info("executeLocalTransaction transactionId:[{}],[{}]", msg.getTransactionId(), arg);
        } catch (Exception e) {
            log.error("", e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        try {
            log.info("checkLocalTransaction transactionId:[{}]", msg.getTransactionId());
        } catch (Exception e) {
            log.error("", e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
