// --- Message.java (JPA - JDBC version)
package com.example.virtual_reactive_comparison.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(String text, LocalDateTime timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
