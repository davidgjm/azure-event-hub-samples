package com.example.messaging.amqp10.qpid.transport;

import com.example.messaging.amqp10.qpid.config.AmqpBrokerProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Slf4j
@EnableConfigurationProperties(AmqpBrokerProperties.class)
@Service
public class MessagePublishServiceImpl implements MessagePublishService {
    private final AmqpBrokerProperties amqpBrokerProperties;
    private final ObjectMapper objectMapper;
    private final Connection connection;

    public MessagePublishServiceImpl(AmqpBrokerProperties amqpBrokerProperties, ObjectMapper objectMapper, Connection connection) {
        this.amqpBrokerProperties = amqpBrokerProperties;
        this.objectMapper = objectMapper;
        this.connection = connection;
    }

    @Override
    public <T> void publish(T message) throws JMSException {
        doSend(acquireSession(), message);
    }

    private Session acquireSession() throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    private <T> void doSend(Session session, T message) throws JMSException {
        MessageProducer messageProducer = session.createProducer(new JmsTopic(amqpBrokerProperties.getTopic()));
        String json = null;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message as json!", e);
            return;
        }
        TextMessage jmsMessage = session.createTextMessage(json);
        messageProducer.send(jmsMessage);
    }
}
