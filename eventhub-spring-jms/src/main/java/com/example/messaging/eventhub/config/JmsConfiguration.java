package com.example.messaging.eventhub.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;

import javax.jms.ConnectionFactory;

@Slf4j
@EnableJms
@EnableConfigurationProperties(EventHubProperties.class)
@Configuration(proxyBeanMethods = false)
public class JmsConfiguration {
    private final EventHubProperties eventHubProperties;

    public JmsConfiguration(EventHubProperties eventHubProperties) {
        this.eventHubProperties = eventHubProperties;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                          DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setTransactionManager(null);
        factory.setSessionTransacted(false);
        factory.setConcurrency("3-10");
        factory.setRecoveryInterval(5000L);
        return factory;
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        var url = eventHubProperties.buildUrl();
        log.debug("Event Hub amqp url: {}", url);
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(url);
        return new SingleConnectionFactory(jmsConnectionFactory);
    }

}
