package com.siem.backend.controller;

import com.siem.backend.producer.LogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogProducer logProducer;

    @PostMapping
    public String sendLog(@RequestBody String logJson) {
        logProducer.sendLog(logJson);
        return "Log sent to Kafka!";
    }
}
