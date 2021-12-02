package com.example.messaging.azure.eventhub.amqp;

import com.azure.spring.integration.core.EventHubHeaders;
import com.azure.spring.integration.core.api.reactor.Checkpointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import static com.azure.spring.integration.core.AzureHeaders.CHECKPOINTER;

@Slf4j
@SpringBootApplication
public class CloudStreamBinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudStreamBinderApplication.class, args);
    }


    @Bean
    public Consumer<Message<String>> consume() {
        return message -> {
            Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
            log.info("New message received: '{}', partition key: {}, sequence number: {}, offset: {}, enqueued time: {}",
                    message.getPayload(),
                    message.getHeaders().get(EventHubHeaders.PARTITION_KEY),
                    message.getHeaders().get(EventHubHeaders.SEQUENCE_NUMBER),
                    message.getHeaders().get(EventHubHeaders.OFFSET),
                    message.getHeaders().get(EventHubHeaders.ENQUEUED_TIME)
            );
            checkpointer.success()
                    .doOnSuccess(success -> log.info("Message '{}' successfully checkpointed", message.getPayload()))
                    .doOnError(error -> log.error("Exception found", error))
                    .subscribe();
        };
    }
}
