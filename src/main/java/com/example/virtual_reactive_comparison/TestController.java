// TestController.java
package com.example.virtual_reactive_comparison;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final Environment environment;

    public TestController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/")
    public String hello() {
        String[] profiles = environment.getActiveProfiles();
        if(profiles.length == 0) {
            return "No active profile. Default backend is running.";
        }
        return "✅ Active Profile: " + String.join(", ", profiles) + " backend is running!";
    }
}