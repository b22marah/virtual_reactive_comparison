// MessageService.java (Reactive branch)
package com.example.virtual_reactive_comparison.service;

import com.example.virtual_reactive_comparison.model.ReactiveMessage;
import com.example.virtual_reactive_comparison.repository.ReactiveMessageRepository;
import com.example.virtual_reactive_comparison.util.CsvLogger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final ReactiveMessageRepository reactiveMessageRepository;

    public MessageService(ReactiveMessageRepository reactiveMessageRepository) {
        this.reactiveMessageRepository = reactiveMessageRepository;
    }

    public Mono<ReactiveMessage> save(String text) {
        ReactiveMessage msg = new ReactiveMessage(text, LocalDateTime.now());
        return reactiveMessageRepository.save(msg);
    }

    public Flux<ReactiveMessage> findAll() {
        return reactiveMessageRepository.findAll();
    }

    public Mono<Void> benchmarkInsert(int count) {
        Mono<Void> insertTask = Flux.range(0, count)
                .flatMap(i -> save("Reactive Message " + i))
                .then();

        return CsvLogger.benchmarkAndLog("benchmark-reactive.csv", "insert", count, insertTask);
    }
}