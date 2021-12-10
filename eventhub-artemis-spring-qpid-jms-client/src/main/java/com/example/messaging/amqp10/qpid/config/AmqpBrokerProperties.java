package com.example.messaging.amqp10.qpid.config;

import com.example.messaging.amqp10.qpid.UnsupportedAmqpBrokerException;
import com.example.messaging.amqp10.qpid.models.AmqpBrokerType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Data
@ConfigurationProperties(prefix = "amqp.broker")
public class AmqpBrokerProperties {
    private String hostName;
    private Integer port;
    private boolean tlsEnabled;
    private AmqpBrokerType brokerType;
    private String username;
    private String password;
    private String topic;
    private String tracing;
    private Map<String, String> properties;

    public String buildUrl() {
        return String.format("%s://%s:%d/?jms.username=%s&jms.password=%s&jms.tracing=%s",
                tlsEnabled ? "amqps" : "amqp",
                hostName,
                port,
                username,
                password,
                tracing
        );
    }

    public JmsTopic jmsTopic() {
        switch (brokerType) {
            case ARTEMIS:
                return new JmsTopic(topic);
            case AZURE_EVENT_HUB:
                return new JmsTopic(getEventHubPartitionedTopic());
        }
        throw new UnsupportedAmqpBrokerException(brokerType.name());
    }

    private String getEventHubPartitionedTopic() {
        if (CollectionUtils.isEmpty(properties)) {
            return String.format("%s/ConsumerGroups/$Default/Partitions/0", topic);
        }
        String consumerGroup = properties.getOrDefault(AmqpExtendedConfig.CONSUMER_GROUP, "$Default");
        Integer partition = Integer.parseInt(properties.getOrDefault(AmqpExtendedConfig.CONSUMER_PARTITION, "0"));
        log.debug("Configuration - consumer group={}, partition={}", consumerGroup, partition);
        String partitionedTopic = String.format("%s/ConsumerGroups/%s/Partitions/%d", topic, consumerGroup, partition);
        log.debug("Configuration - partitioned topic = '{}'", partitionedTopic);
        return partitionedTopic;
    }
}
