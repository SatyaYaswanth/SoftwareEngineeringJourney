package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.UserRequest;
import com.codepilot.springbootbasics.dto.UserResponse;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.model.User;
import com.codepilot.springbootbasics.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponse createUser(UserRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public UserResponse updateUser(Long id, UserRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

        User updatedUser = userRepository.save(existingUser);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);
    }

    public List<UserResponse> searchUsers(String name) {

        return userRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }
}