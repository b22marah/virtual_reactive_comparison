// CsvLogger.java
package com.example.virtual_reactive_comparison.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class CsvLogger {
    private final String filePath;

    public CsvLogger(String filePath) {
        this.filePath = filePath;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("timestamp,testType,concurrency,latencyMs,throughput,cpuUsagePercent,heapMemoryMB");
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize CSV file", e);
        }
    }

    public void log(String testType, int concurrency, long latencyMs, double throughput, double cpuUsage, double heapMemoryMB) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.printf("%s,%s,%d,%d,%.2f,%.2f,%.2f%n",
                    LocalDateTime.now(), testType, concurrency, latencyMs, throughput, cpuUsage, heapMemoryMB);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to CSV", e);
        }
    }
}