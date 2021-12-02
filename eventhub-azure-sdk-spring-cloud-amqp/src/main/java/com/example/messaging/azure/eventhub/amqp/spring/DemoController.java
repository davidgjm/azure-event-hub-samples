package com.example.messaging.azure.eventhub.amqp.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {
    private final EventHubOpService eventHubOpService;

    public DemoController(EventHubOpService eventHubOpService) {
        this.eventHubOpService = eventHubOpService;
    }

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestBody String message) {
        log.info("Going to add message {} to {}}.", message, topic);
        eventHubOpService.send(topic, message);
        return ResponseEntity.ok(message);
    }
}