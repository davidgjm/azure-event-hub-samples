package com.example.messaging.amqp10.qpid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QpidJmsOverMultiAmqpBrokerApplication {
    public static void main(String[] args) {
        SpringApplication.run(QpidJmsOverMultiAmqpBrokerApplication.class, args);
    }
}
