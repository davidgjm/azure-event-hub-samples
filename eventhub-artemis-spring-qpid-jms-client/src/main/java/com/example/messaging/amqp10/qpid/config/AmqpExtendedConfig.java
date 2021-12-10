package com.example.messaging.amqp10.qpid.config;

public class AmqpExtendedConfig {
    private AmqpExtendedConfig() {
        throw new AssertionError("call to default constructor not expected");
    }

    public static final String CONSUMER_GROUP = "consumer.group";
    public static final String CONSUMER_PARTITION = "consumer.partition";
}
