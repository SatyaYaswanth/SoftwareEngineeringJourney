package com.codepilot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @PostConstruct
    public void intialize(){
        System.out.println("NotificationService intialized!");
    }

    public void sendNotification() {
        System.out.println("Notification sent!");
    }
}