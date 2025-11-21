package com.siem.backend.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendLog(String json) {
        kafkaTemplate.send("logs.security", json);
    }
}
