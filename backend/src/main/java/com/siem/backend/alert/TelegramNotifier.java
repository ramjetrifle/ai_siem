package com.siem.backend.alert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class TelegramNotifier {
    private static final String BOT_TOKEN = "REDACTED_BOT_TOKEN";
    private static final String CHAT_ID = "REDACTED_CHAT_ID";


    public void send(String message) {
        try {
            String urlString = String.format(
                    "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    BOT_TOKEN,
                    CHAT_ID,
                    URLEncoder.encode(message, StandardCharsets.UTF_8)
            );

            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");
            conn.getInputStream().close();

            System.out.println("Sent Telegram alert");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
