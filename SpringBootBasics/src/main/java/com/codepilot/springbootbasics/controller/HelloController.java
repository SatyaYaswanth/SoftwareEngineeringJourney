package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.model.HealthResponse;
import com.codepilot.springbootbasics.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final GreetingService greetingService;

    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from CodePilot!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to CodePilot Backend!";
    }

    @GetMapping("/about")
    public String about() {
        return "CodePilot Developer API";
    }

    @GetMapping("/status")
    public String status() {
        return "CodePilot Backend is running!";
    }

    @GetMapping("/greeting")
    public String greeting() {
        return greetingService.getGreeting();
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP", "CodePilot");
    }
}