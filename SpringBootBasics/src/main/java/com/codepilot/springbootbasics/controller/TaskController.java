package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.TaskCreateRequest;
import com.codepilot.springbootbasics.dto.TaskResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

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

    @GetMapping
    public List<TaskResponse> getProjectTasks(
            @PathVariable Long projectId,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return taskService.getProjectTasks(
                projectId,
                user.getEmail()
        );
    }

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
