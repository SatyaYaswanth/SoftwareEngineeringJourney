
package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.ProjectCreateRequest;
import com.codepilot.springbootbasics.dto.ProjectResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.codepilot.springbootbasics.dto.PageResponse;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/workspaces/{workspaceId}/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @PathVariable Long workspaceId,
            @Valid @RequestBody ProjectCreateRequest request,
            Authentication authentication) {

        String authenticatedEmail =
                authentication.getPrincipal()
                        instanceof CodePilotUser user
                        ? user.getEmail()
                        : authentication.getName();

        return projectService.createProject(
                workspaceId,
                request,
                authenticatedEmail
        );
    }




    @GetMapping("/workspaces/{workspaceId}/projects")
    public PageResponse<ProjectResponse> getWorkspaceProjects(
            @PathVariable Long workspaceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Authentication authentication) {

        String authenticatedEmail =
                authentication.getPrincipal()
                        instanceof CodePilotUser user
                        ? user.getEmail()
                        : authentication.getName();

        return projectService.getWorkspaceProjects(
                workspaceId,
                authenticatedEmail,
                page,
                size,
                sortBy,
                direction
        );
    }




    @GetMapping("/projects/{projectId}")
    public ProjectResponse getProject(
            @PathVariable Long projectId,
            Authentication authentication) {

        String authenticatedEmail =
                authentication.getPrincipal()
                        instanceof CodePilotUser user
                        ? user.getEmail()
                        : authentication.getName();

        return projectService.getProject(
                projectId,
                authenticatedEmail
        );
    }

}
