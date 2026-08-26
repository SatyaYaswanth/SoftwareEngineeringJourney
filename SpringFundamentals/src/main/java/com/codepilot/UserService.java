package com.codepilot;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final NotificationService notificationService;
    private final EmailService emailService;

    public UserService(NotificationService notificationService,
                       EmailService emailService) {
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    public void registerUser() {

        System.out.println("User registered!");

        notificationService.sendNotification();
        emailService.sendEmail();
    }
}