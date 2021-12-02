package com.example.messaging.eventhub.qpid.jms.transport;

import com.example.messaging.eventhub.qpid.jms.config.EventHubProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsTopic;

import javax.jms.*;

@Slf4j
public class MessageConsumerService {
    private final EventHubProperties eventHubProperties;
    private final Connection connection;
    private final MessageListener messageListener;

    public MessageConsumerService(EventHubProperties eventHubProperties, Connection connection, MessageListener messageListener) {
        this.eventHubProperties = eventHubProperties;
        this.connection = connection;
        this.messageListener = messageListener;
    }

    public void monitorMessages() throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer messageConsumer = session.createConsumer(new JmsTopic(String.format("%s/ConsumerGroups/$Default/Partitions/0", eventHubProperties.getTopic())));
        messageConsumer.setMessageListener(messageListener);
    }
}
