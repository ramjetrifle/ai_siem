package com.siem.backend.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    @KafkaListener(topics = "logs.security", groupId = "log-consumer-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Consumed from Kafka: " + record.value());
    }
}
