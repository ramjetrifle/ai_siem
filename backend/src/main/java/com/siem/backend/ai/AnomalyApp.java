package com.siem.backend.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siem.backend.alert.TelegramNotifier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

public class AnomalyApp {
    private static final File logDir = new File("src/main/resources");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final GeminiEnrichmentService gemini = new GeminiEnrichmentService();
    private static final TelegramNotifier notifier = new TelegramNotifier();

    private static final KafkaProducer<String, String> kafkaProducer = createKafkaProducer();
    private static final String ELASTIC_TOPIC = "logs-security";

    public static void main(String[] args) throws Exception {
        File[] files = logDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.err.println("No JSON log files found in " + logDir.getPath());
            return;
        }

        for (File file : files) {
            List<LogEntry> logs = LogJsonParser.parseLogFile(file);
            for (LogEntry log : logs) {
                String jsonLog = mapper.writeValueAsString(log);
                boolean isAnomaly = gemini.isAnomalous(jsonLog);

                System.out.printf("[%s] Is this an anomaly? %s%n", file.getName(), isAnomaly ? "Yes" : "No");

                kafkaProducer.send(new ProducerRecord<>(ELASTIC_TOPIC, jsonLog));

                if (isAnomaly) {
                    notifier.send("🚨 Anomaly detected in " + file.getName() + ":\n" + log);
                }
            }
        }

        kafkaProducer.flush();
        kafkaProducer.close();

        System.out.println("Finished scanning all logs.");
    }

    private static KafkaProducer<String, String> createKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
}
