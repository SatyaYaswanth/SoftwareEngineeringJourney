
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.WorkspaceCreateRequest;
import com.codepilot.springbootbasics.dto.WorkspaceResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Workspace;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final CodePilotUserRepository codePilotUserRepository;

    public WorkspaceService(
            WorkspaceRepository workspaceRepository,
            CodePilotUserRepository codePilotUserRepository) {
        this.workspaceRepository = workspaceRepository;
        this.codePilotUserRepository = codePilotUserRepository;
    }

    @Transactional
    public WorkspaceResponse createWorkspace(
            WorkspaceCreateRequest request,
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        Workspace workspace = new Workspace(
                request.getName(),
                user
        );

        Workspace savedWorkspace =
                workspaceRepository.save(workspace);

        return new WorkspaceResponse(
                savedWorkspace.getId(),
                savedWorkspace.getName()
        );
    }

    @Transactional(readOnly = true)
    public List<WorkspaceResponse> getMyWorkspaces(
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        return workspaceRepository.findByUserId(user.getId())
                .stream()
                .map(workspace -> new WorkspaceResponse(
                        workspace.getId(),
                        workspace.getName()
                ))
                .toList();
    }


    @Transactional(readOnly = true)
    public WorkspaceResponse getWorkspace(
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

        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getName()
        );
    }

    @Transactional
    public WorkspaceResponse updateWorkspace(
            Long workspaceId,
            WorkspaceCreateRequest request,
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

        workspace.setName(request.getName());

        Workspace updatedWorkspace =
                workspaceRepository.save(workspace);

        return new WorkspaceResponse(
                updatedWorkspace.getId(),
                updatedWorkspace.getName()
        );
    }

    @Transactional
    public void deleteWorkspace(
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

        workspaceRepository.delete(workspace);
    }

}
