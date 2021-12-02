package com.example.messaging.eventhub.qpid.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QpidJmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(QpidJmsClientApplication.class, args);
    }
}
