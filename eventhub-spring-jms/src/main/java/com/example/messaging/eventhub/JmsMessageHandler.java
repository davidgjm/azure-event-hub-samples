package com.example.messaging.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
public class JmsMessageHandler {

    @JmsListener(destination = "${azure.eventhub.topic}")
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
