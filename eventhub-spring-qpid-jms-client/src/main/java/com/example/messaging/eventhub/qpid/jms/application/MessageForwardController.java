package com.example.messaging.eventhub.qpid.jms.application;

import com.example.messaging.eventhub.qpid.jms.mdels.DemoEventMessage;
import com.example.messaging.eventhub.qpid.jms.transport.MessagePublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.jms.JMSException;

@Slf4j
@RestController
public class MessageForwardController {
    private final MessagePublishService publishService;

    public MessageForwardController(MessagePublishService publishService) {
        this.publishService = publishService;
    }

    @PostMapping("/messages")
    public Mono<Void> forward(@RequestBody DemoEventMessage eventMessage) throws JMSException {
        log.info("Got user event message: {}", eventMessage);
        publishService.publish(eventMessage);
        return Mono.empty();
    }
}
