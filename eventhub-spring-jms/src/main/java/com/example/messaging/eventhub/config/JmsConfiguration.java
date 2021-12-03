package com.example.messaging.eventhub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import javax.jms.ConnectionFactory;

@Slf4j
@EnableJms
@EnableConfigurationProperties(EventHubProperties.class)
@Configuration(proxyBeanMethods = false)
public class JmsConfiguration {
    private final ObjectMapper objectMapper;
    private final EventHubProperties eventHubProperties;

    public JmsConfiguration(ObjectMapper objectMapper, EventHubProperties eventHubProperties) {
        this.objectMapper = objectMapper;
        this.eventHubProperties = eventHubProperties;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                          DefaultJmsListenerContainerFactoryConfigurer configurer) {
        var messageConverter = new org.springframework.jms.support.converter.MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setTransactionManager(null);
        factory.setSessionTransacted(false);
        factory.setConcurrency("1-5");
        factory.setMessageConverter(messageConverter);
        factory.setRecoveryInterval(5000L);
        return factory;
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        var url = eventHubProperties.buildUrl();
        log.debug("Event Hub amqp url: {}", url);
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(url);
        return new CachingConnectionFactory(jmsConnectionFactory);
    }


    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        JmsMessagingTemplate messagingTemplate = new JmsMessagingTemplate(jmsTemplate);
        messagingTemplate.setMessageConverter(messageConverter);
        messagingTemplate.setDefaultDestinationName(jmsTemplate.getDefaultDestinationName());
        return messagingTemplate;
    }
}
