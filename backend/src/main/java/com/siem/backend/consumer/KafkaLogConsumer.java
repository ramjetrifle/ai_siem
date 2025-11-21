package com.siem.backend.consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siem.backend.ai.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaLogConsumer {

    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public KafkaLogConsumer(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @KafkaListener(topics = "logs.security", groupId = "log-consumer-group")
    public void consume(String message) {
        System.out.println("Kafka consumed: " + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            LogEntry log = mapper.readValue(message, LogEntry.class);

            IndexRequest<LogEntry> request = IndexRequest.of(i -> i
                    .index("logs-security")
                    .document(log)
            );

            IndexResponse response = elasticsearchClient.index(request);
            System.out.println("Indexed with ID: " + response.id());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
