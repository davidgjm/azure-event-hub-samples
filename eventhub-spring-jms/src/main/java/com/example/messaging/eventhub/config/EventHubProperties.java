package com.example.messaging.eventhub.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "azure.eventhub")
public class EventHubProperties {
    private String hostName;
    private Integer port;
    private String sharedAccessKeyName;
    private String sharedAccessKey;
    private String tracing;

    public String buildUrl() {
        return String.format("amqps://%s:%d/?jms.username=%s&jms.password=%s&jms.tracing=%s&amqp.idleTimeout=120000&amqp.traceFrames=true",
                hostName,
                port,
                sharedAccessKeyName,
                sharedAccessKey,
                tracing
                );
    }
}
