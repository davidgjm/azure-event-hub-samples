package com.example.messaging.eventhub.qpid.jms.transport;

import javax.jms.JMSException;

public interface MessagePublishService {

    <T> void publish(T message) throws JMSException;
}
