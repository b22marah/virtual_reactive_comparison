package com.example.virtual_reactive_comparison.controller;

import com.example.virtual_reactive_comparison.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/benchmark")
    public ResponseEntity<String> benchmarkInsert(@RequestParam(defaultValue = "100") int count) {
        messageService.benchmarkInsert(count);
        return ResponseEntity.ok("Benchmark complete.");
    }

    @GetMapping("/benchmark/fetch")
    public ResponseEntity<String> benchmarkFetch(@RequestParam(defaultValue = "100") int count) {
        messageService.benchmarkFetch(count);
        return ResponseEntity.ok("Fetch benchmark completed. CSV updated.");
    }
}
