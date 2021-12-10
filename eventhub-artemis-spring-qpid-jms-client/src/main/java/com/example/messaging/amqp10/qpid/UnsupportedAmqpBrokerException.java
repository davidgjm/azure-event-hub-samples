package com.example.messaging.amqp10.qpid;

public class UnsupportedAmqpBrokerException extends RuntimeException{
    public UnsupportedAmqpBrokerException(String message) {
        super(message);
    }

    public UnsupportedAmqpBrokerException() {
    }
}
