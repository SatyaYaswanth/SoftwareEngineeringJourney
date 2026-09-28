package com.codepilot.springbootbasics.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "mypassword123";

        String encodedPassword = encoder.encode(password);

        System.out.println("Original: " + password);
        System.out.println("Encoded: " + encodedPassword);
        System.out.println("Matches: " +
                encoder.matches(password, encodedPassword));
    }
}