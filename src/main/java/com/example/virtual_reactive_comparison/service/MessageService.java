package com.example.virtual_reactive_comparison.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;
import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.repository.MessageRepository;
import com.example.virtual_reactive_comparison.util.CsvLogger;
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

    public void clearAll() {
        messageRepository.deleteAll();
    }

    public void benchmarkInsert(int count) {
        CsvLogger.benchmarkAndLog("benchmark-jdbc.csv", "insert", count, () -> {
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<Callable<Void>> tasks = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    final int index = i;
                    tasks.add(() -> {
                        save("JDBC Message " + index);
                        return null;
                    });
                }
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Benchmark insert interrupted", e);
            }
        });
    }

    public void benchmarkFetch(int concurrency) {
        CsvLogger.benchmarkAndLog("benchmark-jdbc.csv", "fetch", concurrency, () -> {
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<Callable<Void>> tasks = new ArrayList<>();
                for (int i = 0; i < concurrency; i++) {
                    tasks.add(() -> {
                        messageRepository.findAll();
                        return null;
                    });
                }
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Benchmark fetch interrupted", e);
            }
        });
    }
}
