
package com.codepilot.springbootbasics.dto;

import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating or updating a task")
public record TaskCreateRequest(

        @Schema(
                description = "Title of the task",
                example = "Implement JWT Authentication",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Task title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        @Schema(
                description = "Detailed description of the task",
                example = "Implement JWT-based authentication using Spring Security"
        )
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @Schema(
                description = "Current status of the task",
                example = "TODO"
        )
        TaskStatus status,

        @Schema(
                description = "Priority level of the task",
                example = "HIGH"
        )
        TaskPriority priority
) {
}
