package com.example.messaging.eventhub.qpid.jms.config;

import com.example.messaging.eventhub.qpid.jms.transport.JmsExceptionListener;
import com.example.messaging.eventhub.qpid.jms.transport.JmsMessageHandler;
import com.example.messaging.eventhub.qpid.jms.transport.MessageConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;

@Slf4j
@EnableConfigurationProperties(EventHubProperties.class)
@Configuration(proxyBeanMethods = false)
public class EventHubQpidJmsConfiguration {
    private final EventHubProperties eventHubProperties;

    public EventHubQpidJmsConfiguration(EventHubProperties eventHubProperties) {
        this.eventHubProperties = eventHubProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var url = eventHubProperties.buildUrl();
        log.debug("Event Hub amqp url: {}", url);
        return new JmsConnectionFactory(url);
    }

    @Bean(destroyMethod = "stop")
    public Connection jmsConnection(ConnectionFactory factory) throws JMSException {
        Connection connection = factory.createConnection();
        connection.setExceptionListener(new JmsExceptionListener());
        connection.start();
        return connection;
    }

    @Bean
    public MessageListener messageListener() {
        return new JmsMessageHandler();
    }

    @Bean
    public MessageConsumerService messageConsumer(Connection connection, MessageListener messageListener) throws JMSException {
        MessageConsumerService messageConsumerService = new MessageConsumerService(eventHubProperties, connection, messageListener);
        messageConsumerService.monitorMessages();
        return messageConsumerService;
    }
}
