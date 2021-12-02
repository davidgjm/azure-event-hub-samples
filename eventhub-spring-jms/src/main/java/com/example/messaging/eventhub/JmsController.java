package com.example.messaging.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class JmsController {

    private final JmsTemplate jmsTemplate;


    public JmsController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/messages")
    public void send(@RequestBody String message, @RequestParam(required = false) String topic) {
        log.info("Received user message: {}", message);
        publishToTopic(topic, message);

    }

    private <T> void publishToTopic(String topic, T message) {
        if (StringUtils.hasText(topic)) {
            log.info("Attempting to publish message to topic {}. Message: {}", topic, message);
            jmsTemplate.convertAndSend(topic, message);
        } else {
            log.info("Sending message to default topic. Message: {}", message);
            jmsTemplate.convertAndSend(message);
        }
    }

//    private void startConsumer() {
//        if (!messageListenerContainer.isRunning()) {
//            log.warn("messageListenerContainer not running. Trying to start....");
//            messageListenerContainer.start();
//        }
//    }
}
