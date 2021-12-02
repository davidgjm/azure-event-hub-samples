package com.example.messaging.azure.eventhub.amqp.spring;

import com.azure.spring.integration.eventhub.api.EventHubOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class EventHubOpServiceImpl implements EventHubOpService {
    private final EventHubOperation eventHubOperation;

    public EventHubOpServiceImpl(EventHubOperation eventHubOperation) {
        Assert.notNull(eventHubOperation, "EventHubOperation instance cannot be null!");
        this.eventHubOperation = eventHubOperation;
    }

    @Override
    public void send(String topic, String message) {
        log.info("Attempting to send message: {}", message);
        var msg = MessageBuilder.withPayload(message).build();
        eventHubOperation.sendAsync(topic, msg).block();
    }
}
