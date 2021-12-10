package com.example.messaging.amqp10.qpid.transport;

import javax.jms.JMSException;

public interface MessagePublishService {

    <T> void publish(T message) throws JMSException;
}
