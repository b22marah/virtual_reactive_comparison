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
        return ResponseEntity.ok(messageService.save(text));
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }
}
