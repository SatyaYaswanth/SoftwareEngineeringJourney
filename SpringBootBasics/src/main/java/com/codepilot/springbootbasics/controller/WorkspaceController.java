
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

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

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

    @GetMapping
    public List<WorkspaceResponse> getMyWorkspaces(
            Authentication authentication) {

        CodePilotUser user =
                (CodePilotUser) authentication.getPrincipal();

        return workspaceService.getMyWorkspaces(
                user.getEmail()
        );
    }


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
