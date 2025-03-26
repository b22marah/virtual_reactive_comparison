// TestController.java
package com.example.virtual_reactive_comparison;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "✅ Reactive backend is running!";
    }
}
