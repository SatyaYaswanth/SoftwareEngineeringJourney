
package com.codepilot.springbootbasics.dto;

import com.codepilot.springbootbasics.entity.Task;
import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task details returned by the API")
public record TaskResponse(

        @Schema(description = "Unique task ID", example = "101")
        Long id,

        @Schema(description = "Task title", example = "Implement JWT Authentication")
        String title,

        @Schema(description = "Task description", example = "Implement JWT security")
        String description,

        @Schema(description = "Current task status", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Task priority", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "ID of the project containing this task", example = "10")
        Long projectId
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getProject().getId()
        );
    }
}
