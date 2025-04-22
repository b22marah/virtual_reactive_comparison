package com.example.virtual_reactive_comparison.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;
import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.repository.MessageRepository;
import com.example.virtual_reactive_comparison.util.CsvLogger;
import com.example.virtual_reactive_comparison.util.SystemMetrics;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(String text) {
        Message message = new Message(text, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public void benchmarkInsert(int count) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            save("JDBC Message " + i);
        }

        long end = System.currentTimeMillis();
        long latency = end - start;
        double throughput = (double) count / (latency / 1000.0);

        double cpuUsage = SystemMetrics.getProcessCpuLoad();
        long heapUsed = SystemMetrics.getHeapMemoryUsage();

        CsvLogger logger = new CsvLogger("benchmark-jdbc.csv");
        logger.log("insert", count, latency, throughput, cpuUsage, heapUsed);
    }

    public void benchmarkFetch(int concurrency) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<Void>> tasks = new ArrayList<>();

            for (int i = 0; i < concurrency; i++) {
                tasks.add(() -> {
                    messageRepository.findAll();
                    return null;
                });
            }

            long start = System.nanoTime();
            double cpuBefore = SystemMetrics.getProcessCpuLoad();
            long heapBefore = SystemMetrics.getHeapMemoryUsage();

            executor.invokeAll(tasks);

            long end = System.nanoTime();
            double cpuAfter = SystemMetrics.getProcessCpuLoad();
            long heapAfter = SystemMetrics.getHeapMemoryUsage();

            long latencyMs = (end - start) / 1_000_000;
            double throughput = (double) concurrency / (latencyMs / 1000.0);
            double cpuUsage = (cpuBefore + cpuAfter) / 2.0;
            long heapMemoryMB = (heapBefore + heapAfter) / 2 / (1024 * 1024);

            CsvLogger logger = new CsvLogger("benchmark-jdbc.csv");
            logger.log("fetch", concurrency, latencyMs, throughput, cpuUsage, heapMemoryMB);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Benchmark fetch interrupted", e);
        }
    }
}
