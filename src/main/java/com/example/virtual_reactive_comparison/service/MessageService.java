package com.example.virtual_reactive_comparison.service;

import com.example.virtual_reactive_comparison.model.ReactiveMessage;
import com.example.virtual_reactive_comparison.repository.ReactiveMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired(required = false)
    private ReactiveMessageRepository reactiveMessageRepository;

    public Mono<Void> insertMessagesReactive(int count) {
        return Flux.range(0, count)
                .flatMap(i -> {
                    ReactiveMessage msg = new ReactiveMessage("Reactive Message " + i, LocalDateTime.now());
                    return reactiveMessageRepository.save(msg);
                })
                .then();
    }

    public Mono<ReactiveMessage> saveReactive(String text) {
        ReactiveMessage msg = new ReactiveMessage(text, LocalDateTime.now());
        return reactiveMessageRepository.save(msg);
    }

    public Flux<ReactiveMessage> findAllReactive() {
        return reactiveMessageRepository.findAll();
    }
}
