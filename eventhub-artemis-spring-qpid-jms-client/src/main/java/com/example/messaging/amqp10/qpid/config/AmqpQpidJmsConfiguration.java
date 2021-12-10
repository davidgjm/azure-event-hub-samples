package com.example.messaging.amqp10.qpid.config;


import com.example.messaging.amqp10.qpid.transport.JmsExceptionListener;
import com.example.messaging.amqp10.qpid.transport.JmsMessageHandler;
import com.example.messaging.amqp10.qpid.transport.MessageConsumerService;
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
@EnableConfigurationProperties(AmqpBrokerProperties.class)
@Configuration(proxyBeanMethods = false)
public class AmqpQpidJmsConfiguration {
    private final AmqpBrokerProperties amqpBrokerProperties;

    public AmqpQpidJmsConfiguration(AmqpBrokerProperties amqpBrokerProperties) {
        this.amqpBrokerProperties = amqpBrokerProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var url = amqpBrokerProperties.buildUrl();
        log.debug("AMQP Broker url: {}", url);
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
        MessageConsumerService messageConsumerService = new MessageConsumerService(amqpBrokerProperties, connection, messageListener);
        messageConsumerService.monitorMessages();
        return messageConsumerService;
    }

}

