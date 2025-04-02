// --- MessageRepository.java (JPA - JDBC)
package com.example.virtual_reactive_comparison.repository;

import com.example.virtual_reactive_comparison.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}