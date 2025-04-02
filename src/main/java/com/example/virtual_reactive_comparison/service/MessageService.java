package com.example.virtual_reactive_comparison.service;

import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.model.ReactiveMessage;
import com.example.virtual_reactive_comparison.repository.MessageRepository;
import com.example.virtual_reactive_comparison.repository.ReactiveMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
public class MessageService {

    @Autowired(required = false)
    private MessageRepository messageRepository;

    @Autowired(required = false)
    private ReactiveMessageRepository reactiveMessageRepository;

    // --- Concurrency inserts for testing ---
    public void insertMessagesJDBC(int count) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        IntStream.range(0, count).forEach(i -> executor.submit(() -> {
            Message msg = new Message("JDBC Message " + i, LocalDateTime.now());
            messageRepository.save(msg);
        }));
        executor.shutdown();
    }

    public Mono<Void> insertMessagesReactive(int count) {
        return Flux.range(0, count)
                .flatMap(i -> {
                    ReactiveMessage msg = new ReactiveMessage("Reactive Message " + i, LocalDateTime.now());
                    return reactiveMessageRepository.save(msg);
                })
                .then();
    }

    // --- New endpoints used by MessageController ---
    public Message saveJdbc(String text) {
        Message message = new Message(text, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> findAllJdbc() {
        return messageRepository.findAll();
    }

    public Mono<ReactiveMessage> saveReactive(String text) {
        ReactiveMessage msg = new ReactiveMessage(text, LocalDateTime.now());
        return reactiveMessageRepository.save(msg);
    }

    public Flux<ReactiveMessage> findAllReactive() {
        return reactiveMessageRepository.findAll();
    }
}
