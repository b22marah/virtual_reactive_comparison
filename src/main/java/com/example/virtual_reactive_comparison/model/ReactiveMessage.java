// --- ReactiveMessage.java (R2DBC version)
package com.example.virtual_reactive_comparison.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("message")
public class ReactiveMessage {
    @Id
    private Long id;
    private String text;
    private LocalDateTime timestamp;

    public ReactiveMessage() {}

    public ReactiveMessage(String text, LocalDateTime timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}