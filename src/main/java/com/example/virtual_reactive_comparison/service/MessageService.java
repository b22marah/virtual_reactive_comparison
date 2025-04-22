package com.example.virtual_reactive_comparison.service;

import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.repository.MessageRepository;
import com.example.virtual_reactive_comparison.util.CsvLogger;
import com.example.virtual_reactive_comparison.util.SystemMetrics;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
