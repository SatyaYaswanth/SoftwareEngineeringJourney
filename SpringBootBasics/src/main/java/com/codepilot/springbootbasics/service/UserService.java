package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUsers(){
        return List.of(
                new User(1L, "Alice", "alice@example.com"),
                new User(2L, "Bob", "bob@example.com"),
                new User(3L, "Charlie", "charlie@example.com"),
                new User(4L, "David", "david@example.com"),
                new User(5L, "Emma", "emma@example.com")
        );
    }

    public User getUserById(Long id) {
        return getUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<User> searchUsers(String name) {
        return getUsers()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .toList();
    }

    public User createUser(User user) {
        return user;
    }
}
