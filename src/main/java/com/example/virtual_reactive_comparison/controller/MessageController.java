package com.example.virtual_reactive_comparison.controller;

import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(@RequestBody String text) {
        System.out.println("Handling request on thread: " + Thread.currentThread());
        return ResponseEntity.ok(messageService.save(text));
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        System.out.println("Handling request on thread: " + Thread.currentThread());
        return ResponseEntity.ok(messageService.findAll());
    }

    @PostMapping("/benchmark")
    public ResponseEntity<String> benchmarkInsert(@RequestParam(defaultValue = "100") int count) {
        messageService.benchmarkInsert(count);
        return ResponseEntity.ok("Benchmark complete.");
    }

}
