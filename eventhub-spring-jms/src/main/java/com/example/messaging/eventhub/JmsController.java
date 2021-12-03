package com.example.messaging.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class JmsController {

    private final JmsMessagingTemplate messagingTemplate;

    public JmsController(JmsMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/messages")
    public DemoPayload send(@RequestBody DemoPayload message, @RequestParam(required = false) String topic) {
        log.info("Received user message: {}", message);
        publishToTopic(topic, message);
        return message;
    }

    private <T> void publishToTopic(String topic, T message) {
        if (StringUtils.hasText(topic)) {
            log.info("Attempting to publish message to topic {}. Message: {}", topic, message);
            messagingTemplate.convertAndSend(topic, message);
        } else {
            log.info("Sending message to default topic. Message: {}", message);
            messagingTemplate.convertAndSend(message);
        }

    }

}
