
package com.codepilot.springbootbasics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a project")
public record ProjectCreateRequest(

        @Schema(
                description = "Project name",
                example = "CodePilot",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Project name is required")
        @Size(max = 100, message = "Project name cannot exceed 100 characters")
        String name,

        @Schema(
                description = "Project description",
                example = "AI-powered developer productivity platform"
        )
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description
) {
}
