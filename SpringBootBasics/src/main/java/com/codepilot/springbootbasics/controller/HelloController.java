package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.service.GreetingService;
import org.springframework.web.bind.annotation.*;
import com.codepilot.springbootbasics.model.User;
import java.util.List;
import com.codepilot.springbootbasics.service.UserService;
import com.codepilot.springbootbasics.model.HealthResponse;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final GreetingService greetingService;
    private final UserService userService;

    public HelloController(GreetingService greetingService, UserService userService) {
        this.greetingService = greetingService;
        this.userService = userService;
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

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/search")
    public List<User> search(@RequestParam String name){
        return userService.searchUsers(name);
    }

    @GetMapping("/user")
    public User getUser() {
        return new User(
                1L,
                "Satya",
                "satya@example.com"
        );
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/health")
    public HealthResponse health(){
        return new HealthResponse("UP", "CodePilot");
    }
}