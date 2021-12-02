package com.example.messaging.azure.eventhub.amqp.spring;

public interface EventHubOpService {

    void send(String topic, String message);
}
