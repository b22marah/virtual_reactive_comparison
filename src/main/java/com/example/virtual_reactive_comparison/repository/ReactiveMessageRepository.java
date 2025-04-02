// --- ReactiveMessageRepository.java (R2DBC)
package com.example.virtual_reactive_comparison.repository;

import com.example.virtual_reactive_comparison.model.ReactiveMessage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveMessageRepository extends ReactiveCrudRepository<ReactiveMessage, Long> {
}