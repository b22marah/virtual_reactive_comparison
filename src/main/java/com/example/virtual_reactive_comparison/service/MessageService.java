package com.example.virtual_reactive_comparison.service;

import com.example.virtual_reactive_comparison.model.Message;
import com.example.virtual_reactive_comparison.repository.MessageRepository;
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
}
