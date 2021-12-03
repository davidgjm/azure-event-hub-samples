package com.example.messaging.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.message.JmsBytesMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@Component
public class JmsMessageHandler {

    @JmsListener(destination = "${azure.eventhub.topic}")
    public void onMessage(Message message) {
        log.info("[JMS Consumer] - Received message of type: {}", message.getClass().getName());

        try {
            log.info("Message metadata: correlation-id={}", message.getJMSCorrelationID());
            log.info("Message details: {}", message);
            if (message instanceof JmsBytesMessage) {
                JmsBytesMessage jmsBytesMessage = (JmsBytesMessage) message;
                var body = jmsBytesMessage.getBody(byte[].class);
                log.info("message body: {}", new String(body));
            }
        } catch (JMSException e) {
            log.error("exception when handling message", e);
        }
    }
}
