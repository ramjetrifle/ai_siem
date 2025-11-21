package com.siem.backend.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LogJsonParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<LogEntry> parseLogFile(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<List<LogEntry>>() {});
    }
}
