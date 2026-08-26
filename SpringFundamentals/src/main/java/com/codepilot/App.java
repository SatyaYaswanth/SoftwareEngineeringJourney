package com.codepilot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.codepilot")
public class App {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(App.class);

        UserService userService =
                context.getBean(UserService.class);

        userService.registerUser();

        UserService user1 =
                context.getBean(UserService.class);

        UserService user2 =
                context.getBean(UserService.class);

        System.out.println(user1 == user2);
    }
}