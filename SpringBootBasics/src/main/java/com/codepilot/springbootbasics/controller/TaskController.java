package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.TaskCreateRequest;
import com.codepilot.springbootbasics.dto.TaskResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.codepilot.springbootbasics.dto.PageResponse;
import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.codepilot.springbootbasics.dto.ApiError;


@Tag(
        name = "Task Management",
        description = "Create, retrieve, update and manage project tasks"
)
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @Operation(
            summary = "Create a task",
            description = "Creates a new task inside the specified project."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskCreateRequest request,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return taskService.createTask(
                projectId,
                request,
                user.getEmail()
        );
    }


    @Operation(
            summary = "Get project tasks",
            description = "Retrieves tasks belonging to a project with pagination, sorting and filtering."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping
    public PageResponse<TaskResponse> getProjectTasks(
            @Parameter(description = "Project ID", example = "1")
            @PathVariable Long projectId,

            @Parameter(description = "Page number (starts from 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of tasks per page", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Field used for sorting", example = "id")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sorting direction: asc or desc", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,

            @Parameter(description = "Filter tasks by status", example = "TODO")
            @RequestParam(required = false) TaskStatus status,

            @Parameter(description = "Filter tasks by priority", example = "HIGH")
            @RequestParam(required = false) TaskPriority priority,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return taskService.getProjectTasks(
                projectId,
                user.getEmail(),
                page,
                size,
                sortBy,
                direction,
                status,
                priority
        );
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieves details of a specific task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/{taskId}")
    public TaskResponse getTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return taskService.getTask(
                projectId,
                taskId,
                user.getEmail()
        );
    }

    @Operation(
            summary = "Update task",
            description = "Updates the details of an existing task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid task details"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @PutMapping("/{taskId}")
    public TaskResponse updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskCreateRequest request,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return taskService.updateTask(
                projectId,
                taskId,
                request,
                user.getEmail()
        );
    }

    @Operation(
            summary = "Delete task",
            description = "Deletes a task from the specified project."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        taskService.deleteTask(
                projectId,
                taskId,
                user.getEmail()
        );
    }
}
