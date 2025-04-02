package com.example.virtual_reactive_comparison.controller;

import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.model.ReactiveMessage;
import com.example.virtual_reactive_comparison.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public Object createMessage(@RequestBody String text) {
        if ("jdbc".equals(activeProfile)) {
            return messageService.saveJdbc(text);
        } else if ("reactive".equals(activeProfile)) {
            return messageService.saveReactive(text);
        } else {
            return ResponseEntity.badRequest().body("Unknown profile");
        }
    }

    @GetMapping
    public Object getAllMessages() {
        if ("jdbc".equals(activeProfile)) {
            List<Message> messages = messageService.findAllJdbc();
            return ResponseEntity.ok(messages);
        } else if ("reactive".equals(activeProfile)) {
            Flux<ReactiveMessage> messages = messageService.findAllReactive();
            return messages;
        } else {
            return ResponseEntity.badRequest().body("Unknown profile");
        }
    }
}
