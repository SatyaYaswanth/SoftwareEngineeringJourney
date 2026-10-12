
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

        System.out.println("Project authenticated email: "
                + authenticatedEmail);

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Workspace workspace = workspaceRepository
                .findByIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() ->
                        new UserNotFoundException(
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
    public List<ProjectResponse> getWorkspaceProjects(
            Long workspaceId,
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Workspace workspace = workspaceRepository
                .findByIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Workspace not found with id: " + workspaceId));

        return projectRepository
                .findByWorkspaceId(workspace.getId())
                .stream()
                .map(ProjectResponse::from)
                .toList();
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
                        new UserNotFoundException(
                                "Project not found with id: " + projectId));

        return ProjectResponse.from(project);
    }
}
