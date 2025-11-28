package com.siem.backend.ai;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GeminiEnrichmentService {

    private static final String API_KEY = "API_Key";

    public boolean isAnomalous(String logText) {
        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
            String prompt = "Is this log an anomaly? Answer only 'yes' or 'no'. Log:\n" + logText;

            String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        { "text": "%s" }
                      ]
                    }
                  ]
                }
                """.formatted(prompt.replace("\"", "\\\""));

            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes());
            }

            String response;
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                scanner.useDelimiter("\\A");
                response = scanner.hasNext() ? scanner.next() : "";
                System.out.println("Gemini response:\n" + response);
            } catch (Exception readError) {
                Scanner errorScanner = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
                String errorResponse = errorScanner.hasNext() ? errorScanner.next() : "No error response body";
                System.err.println("Error from Gemini:\n" + errorResponse);
                throw readError;
            }

            return response.toLowerCase().contains("yes");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
