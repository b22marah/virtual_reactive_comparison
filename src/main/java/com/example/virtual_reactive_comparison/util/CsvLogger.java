package com.example.virtual_reactive_comparison.util;

import reactor.core.publisher.Mono;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    testType, concurrency, latencyMs, throughput, cpuUsage, heapMemoryMB);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to CSV", e);
        }
    }

    public static Mono<Void> benchmarkAndLog(String fileName, String testType, int count, Mono<Void> task) {
        double cpuBefore = SystemMetrics.getProcessCpuLoad();
        long heapBefore = SystemMetrics.getHeapMemoryUsage();
        long start = System.nanoTime();

        return task.doOnTerminate(() -> {
            long end = System.nanoTime();
            double cpuAfter = SystemMetrics.getProcessCpuLoad();
            long heapAfter = SystemMetrics.getHeapMemoryUsage();

            long latencyMs = (end - start) / 1_000_000;
            double throughput = (double) count / (latencyMs / 1000.0);
            double cpuUsage = (cpuBefore + cpuAfter) / 2.0;
            double heapMemoryMB = (heapBefore + heapAfter) / 2.0 / (1024 * 1024);

            CsvLogger logger = new CsvLogger(fileName);
            logger.log(testType, count, latencyMs, throughput, cpuUsage, heapMemoryMB);
        });
    }
}