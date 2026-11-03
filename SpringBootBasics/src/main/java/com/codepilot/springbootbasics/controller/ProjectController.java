
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.codepilot.springbootbasics.dto.ApiError;

@Tag(
        name = "Project Management",
        description = "Create, retrieve and manage projects within workspaces"
)
@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @Operation(
            summary = "Create a project",
            description = "Creates a new project inside the specified workspace."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
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
                    description = "Workspace not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
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

    @Operation(
            summary = "Get workspace projects",
            description = "Retrieves projects in a workspace with pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Workspace not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/workspaces/{workspaceId}/projects")
    public PageResponse<ProjectResponse> getWorkspaceProjects(
            @PathVariable Long workspaceId,
            @Parameter(description = "Page number (starts from 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of projects per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field used for sorting", example = "id")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sorting direction: asc or desc", example = "asc")
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

    @Operation(
            summary = "Get project by ID",
            description = "Retrieves the details of a specific project."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
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
