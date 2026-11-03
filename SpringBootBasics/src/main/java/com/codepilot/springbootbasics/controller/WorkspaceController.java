
package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.WorkspaceCreateRequest;
import com.codepilot.springbootbasics.dto.WorkspaceResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.codepilot.springbootbasics.dto.ApiError;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }


    @Tag(
            name = "Workspace Management",
            description = "Manage user workspaces in CodePilot"
    )


    @Operation(
            summary = "Create a workspace",
            description = "Creates a new workspace for the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Workspace created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkspaceResponse createWorkspace(
            @Valid @RequestBody WorkspaceCreateRequest request,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return workspaceService.createWorkspace(
                request,
                user.getEmail()
        );
    }


    @Operation(
            summary = "Get all workspaces",
            description = "Retrieves all workspaces belonging to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Workspaces retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            )
    })
    @GetMapping
    public List<WorkspaceResponse> getMyWorkspaces(
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return workspaceService.getMyWorkspaces(
                user.getEmail()
        );
    }


    @Operation(
            summary = "Get workspace by ID",
            description = "Retrieves a specific workspace belonging to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workspace retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Workspace not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public WorkspaceResponse getWorkspace(
            @PathVariable Long id,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return workspaceService.getWorkspace(
                id,
                user.getEmail()
        );
    }

    @Operation(
            summary = "Update workspace",
            description = "Updates the name of an existing workspace."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workspace updated successfully"),
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
    @PutMapping("/{id}")
    public WorkspaceResponse updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceCreateRequest request,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return workspaceService.updateWorkspace(
                id,
                request,
                user.getEmail()
        );
    }

    @Operation(
            summary = "Delete workspace",
            description = "Deletes a workspace belonging to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Workspace deleted successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Workspace not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkspace(
            @PathVariable Long id,
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        workspaceService.deleteWorkspace(
                id,
                user.getEmail()
        );
    }

}
