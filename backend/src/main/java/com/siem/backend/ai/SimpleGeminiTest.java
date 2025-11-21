//package com.siem.backend.ai;
//
//public class SimpleGeminiTest {
//    public static void main(String[] args) {
//        GeminiEnrichmentService gemini = new GeminiEnrichmentService();
//
//        String sampleLog = """
//            {"timestamp": "2025-05-12T12:01:22Z", "source_ip": "192.168.0.10", "destination_ip": "10.0.0.5", "event_type": "failed_login", "username": "admin"}
//        """;
//
//        boolean result = gemini.isAnomalous(sampleLog);
//
//        System.out.println("Is this an anomaly? " + (result ? "Yes" : "No"));
//    }
//}
