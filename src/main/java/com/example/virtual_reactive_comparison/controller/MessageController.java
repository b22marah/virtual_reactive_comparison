package com.example.virtual_reactive_comparison.controller;

import com.example.virtual_reactive_comparison.service.MessageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/benchmark")
    public Mono<String> benchmarkInsert(@RequestParam(defaultValue = "100") int count) {
        return messageService.benchmarkInsert(count)
                .thenReturn("Reactive benchmark complete.");
    }

    @GetMapping("/benchmark/fetch")
    public Mono<String> benchmarkFetch(@RequestParam(defaultValue = "100") int count) {
        return messageService.benchmarkFetch(count)
                .thenReturn("Reactive fetch benchmark completed. CSV updated.");
    }
}
