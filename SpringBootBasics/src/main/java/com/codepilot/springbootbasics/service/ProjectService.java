
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.ProjectCreateRequest;
import com.codepilot.springbootbasics.dto.ProjectResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Project;
import com.codepilot.springbootbasics.entity.Workspace;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.ProjectRepository;
import com.codepilot.springbootbasics.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.codepilot.springbootbasics.exception.ResourceNotFoundException;
import com.codepilot.springbootbasics.dto.PageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final CodePilotUserRepository codePilotUserRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            WorkspaceRepository workspaceRepository,
            CodePilotUserRepository codePilotUserRepository) {
        this.projectRepository = projectRepository;
        this.workspaceRepository = workspaceRepository;
        this.codePilotUserRepository = codePilotUserRepository;
    }

    @Transactional
    public ProjectResponse createProject(
            Long workspaceId,
            ProjectCreateRequest request,
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Workspace workspace = workspaceRepository
                .findByIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workspace not found with id: " + workspaceId));

        Project project = new Project(
                request.name(),
                request.description(),
                workspace
        );

        Project savedProject = projectRepository.save(project);

        return ProjectResponse.from(savedProject);
    }


    @Transactional(readOnly = true)
    public PageResponse<ProjectResponse> getWorkspaceProjects(
            Long workspaceId,
            String authenticatedEmail,
            int page,
            int size,
            String sortBy,
            String direction) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Workspace workspace = workspaceRepository
                .findByIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workspace not found with id: " + workspaceId));

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page must be non-negative and size must be between 1 and 100");
        }

        if (!List.of("id", "name", "description").contains(sortBy)) {
            throw new IllegalArgumentException("Invalid project sort field");
        }

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Direction must be 'asc' or 'desc'");
        }

        Pageable pageable = PageRequest.of(
                page, size, Sort.by(sortDirection, sortBy));

        return PageResponse.from(
                projectRepository
                        .findByWorkspaceId(workspace.getId(), pageable)
                        .map(ProjectResponse::from)
        );
    }


    @Transactional(readOnly = true)
    public ProjectResponse getProject(
            Long projectId,
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Project project = projectRepository
                .findByIdAndWorkspaceUserId(
                        projectId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return ProjectResponse.from(project);
    }
}
