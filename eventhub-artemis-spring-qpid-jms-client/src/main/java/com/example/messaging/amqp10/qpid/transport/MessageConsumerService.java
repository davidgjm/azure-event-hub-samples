package com.example.messaging.amqp10.qpid.transport;

import com.example.messaging.amqp10.qpid.config.AmqpBrokerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsTopic;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Slf4j
@Component
public class MessageConsumerService {
    private final AmqpBrokerProperties amqpBrokerProperties;
    private final Connection connection;
    private final MessageListener messageListener;

    public MessageConsumerService(AmqpBrokerProperties amqpBrokerProperties, Connection connection, MessageListener messageListener) {
        this.amqpBrokerProperties = amqpBrokerProperties;
        this.connection = connection;
        this.messageListener = messageListener;
    }

    public void monitorMessages() throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        JmsTopic jmsTopic = amqpBrokerProperties.jmsTopic();
        log.info("Creating message consumer from topic: '{}'", jmsTopic.getTopicName());
        MessageConsumer messageConsumer = session.createConsumer(jmsTopic);
        messageConsumer.setMessageListener(messageListener);
    }
}
