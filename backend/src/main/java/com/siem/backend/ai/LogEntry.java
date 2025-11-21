package com.siem.backend.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEntry {

    @JsonProperty("@timestamp")
    private String timestamp;

    private String event;
    private String username;
    private String filename;
    private String status;
    private String sourceIp;
    private String newRole;
    private String label;
    private Integer totalFwdPackets;
    private Integer flowDuration;
    private Long bytes;

    public String getTimestamp() {
        return timestamp;
    }

    public String getEvent() {
        return event;
    }

    public String getUsername() {
        return username;
    }

    public String getFilename() {
        return filename;
    }

    public String getStatus() {
        return status;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getNewRole() {
        return newRole;
    }

    public String getLabel() {
        return label;
    }

    public Integer getTotalFwdPackets() {
        return totalFwdPackets;
    }

    public Integer getFlowDuration() {
        return flowDuration;
    }

    public Long getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "@timestamp='" + timestamp + '\'' +
                ", event='" + event + '\'' +
                ", username='" + username + '\'' +
                ", filename='" + filename + '\'' +
                ", status='" + status + '\'' +
                ", sourceIp='" + sourceIp + '\'' +
                ", newRole='" + newRole + '\'' +
                ", label='" + label + '\'' +
                ", totalFwdPackets=" + totalFwdPackets +
                ", flowDuration=" + flowDuration +
                ", bytes=" + bytes +
                '}';
    }
}
