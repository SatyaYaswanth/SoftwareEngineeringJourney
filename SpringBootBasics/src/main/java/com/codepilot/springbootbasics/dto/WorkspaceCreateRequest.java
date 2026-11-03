package com.codepilot.springbootbasics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating or updating a workspace")
public class WorkspaceCreateRequest {

    @Schema(
            description = "Name of the workspace",
            example = "My Development Workspace",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Workspace name is required.")
    @Size(min = 3, max = 100,
            message = "Workspace name must be between 3 and 100 characters")
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
