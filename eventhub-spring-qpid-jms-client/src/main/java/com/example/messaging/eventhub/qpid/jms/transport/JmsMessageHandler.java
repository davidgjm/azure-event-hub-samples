package com.example.messaging.eventhub.qpid.jms.transport;

import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Slf4j
public class JmsMessageHandler implements MessageListener {
    @Override
    public void onMessage(Message message) {
        log.info("[JMS Consumer] - Received message of type: {}", message.getClass().getName());
        try {
            log.info("Message metadata: correlation-id={}", message.getJMSCorrelationID());
            log.info("Message details: {}", message);
        } catch (JMSException e) {
            log.error("exception when handling message", e);
        }
    }
}
