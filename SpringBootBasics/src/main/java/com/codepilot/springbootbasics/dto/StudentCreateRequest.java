package com.codepilot.springbootbasics.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentCreateRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message ="Email is required")
    @Email(message = "Email must be valid")
    private String email;

    public StudentCreateRequest(){

    }

    public StudentCreateRequest(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
