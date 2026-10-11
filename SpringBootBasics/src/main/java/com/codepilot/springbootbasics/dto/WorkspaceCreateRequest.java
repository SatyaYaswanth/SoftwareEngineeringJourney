package com.codepilot.springbootbasics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WorkspaceCreateRequest {

    @NotBlank(message = "Workspace name is required.")
    @Size(min = 3,max = 100, message = "Workspace name must be between 3 and 100 characters")
    private String name;

    public WorkspaceCreateRequest(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
