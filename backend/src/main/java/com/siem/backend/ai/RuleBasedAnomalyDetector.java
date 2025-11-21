package com.siem.backend.ai;

public class RuleBasedAnomalyDetector {

    public static boolean isAnomaly(LogEntry log) {
        return getAnomalyType(log) != null;
    }

    public static String getAnomalyType(LogEntry log) {
        if (log == null) return null;

        String label = log.getLabel();
        if (label != null && !label.equalsIgnoreCase("BENIGN")) {
            return "Generic Anomaly";
        }

        if ("login_attempt".equalsIgnoreCase(log.getEvent()) && "failed".equalsIgnoreCase(log.getStatus())) {
            return "Brute Force Attack";
        }

        if ("login_success".equalsIgnoreCase(log.getEvent()) && log.getSourceIp() != null &&
                !log.getSourceIp().startsWith("192.168.") && !log.getSourceIp().startsWith("10.")) {
            return "Unusual IP Login";
        }

        if ("file_download".equalsIgnoreCase(log.getEvent()) && log.getBytes() != null && log.getBytes() > 1000000000) {
            return "Data Exfiltration";
        }

        if ("privilege_change".equalsIgnoreCase(log.getEvent()) && "root".equalsIgnoreCase(log.getNewRole())) {
            return "Privilege Escalation";
        }

        if (log.getTotalFwdPackets() != null && log.getTotalFwdPackets() > 50) return "Suspicious Packet Flood";
        if (log.getFlowDuration() != null && log.getFlowDuration() > 1000) return "Long Flow Duration";

        return null;
    }
}
