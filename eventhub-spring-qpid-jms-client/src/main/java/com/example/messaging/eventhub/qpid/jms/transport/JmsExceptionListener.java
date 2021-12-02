package com.example.messaging.eventhub.qpid.jms.transport;

import lombok.extern.slf4j.Slf4j;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Slf4j
public class JmsExceptionListener implements ExceptionListener {
    @Override
    public void onException(JMSException exception) {
        log.error("JMS exception occurred!", exception);
        System.exit(2);
    }
}
