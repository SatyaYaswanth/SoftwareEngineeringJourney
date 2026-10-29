
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.PageResponse;
import com.codepilot.springbootbasics.dto.ProjectCreateRequest;
import com.codepilot.springbootbasics.dto.ProjectResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Project;
import com.codepilot.springbootbasics.entity.Workspace;
import com.codepilot.springbootbasics.exception.ResourceNotFoundException;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.ProjectRepository;
import com.codepilot.springbootbasics.repository.WorkspaceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private CodePilotUserRepository codePilotUserRepository;

    @InjectMocks
    private ProjectService projectService;

    private CodePilotUser user;
    private Workspace workspace;

    @BeforeEach
    void setUp() throws Exception {

        user = new CodePilotUser(
                "Test User",
                "test@example.com",
                "hashed-password",
                "USER"
        );

        setGeneratedId(user, 1L);

        workspace = new Workspace("My Workspace", user);
        setGeneratedId(workspace, 10L);
    }

    private void setGeneratedId(Object entity, Long id)
            throws Exception {

        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    void createProject_shouldCreateProject_whenWorkspaceBelongsToUser() {

        String email = "test@example.com";
        Long workspaceId = 10L;

        ProjectCreateRequest request = new ProjectCreateRequest(
                "CodePilot API",
                "Backend development"
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.of(workspace));

        Project savedProject = new Project(
                request.name(),
                request.description(),
                workspace
        );

        when(projectRepository.save(any(Project.class)))
                .thenReturn(savedProject);

        ProjectResponse response = projectService.createProject(
                workspaceId,
                request,
                email
        );

        assertNotNull(response);
        assertEquals("CodePilot API", response.name());
        assertEquals("Backend development", response.description());

        verify(workspaceRepository)
                .findByIdAndUserId(workspaceId, 1L);

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void createProject_shouldThrowException_whenUserDoesNotExist() {

        String email = "missing@example.com";

        ProjectCreateRequest request = new ProjectCreateRequest(
                "CodePilot API",
                "Backend development"
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> projectService.createProject(10L, request, email)
        );

        verify(workspaceRepository, never())
                .findByIdAndUserId(anyLong(), anyLong());

        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void createProject_shouldThrowException_whenWorkspaceIsNotOwnedByUser() {

        String email = "test@example.com";
        Long workspaceId = 99L;

        ProjectCreateRequest request = new ProjectCreateRequest(
                "CodePilot API",
                "Backend development"
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.createProject(
                        workspaceId, request, email
                )
        );

        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void getWorkspaceProjects_shouldReturnProjects_whenWorkspaceBelongsToUser() {

        String email = "test@example.com";

        Project first = new Project(
                "Frontend",
                "User interface",
                workspace
        );

        Project second = new Project(
                "Backend",
                "REST API",
                workspace
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(10L, 1L))
                .thenReturn(Optional.of(workspace));

        when(projectRepository.findByWorkspaceId(
                eq(10L),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(List.of(first, second)));

        PageResponse<ProjectResponse> response =
                projectService.getWorkspaceProjects(
                        10L,
                        email,
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Frontend", response.getContent().get(0).name());
        assertEquals("Backend", response.getContent().get(1).name());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertTrue(response.isFirst());
        assertTrue(response.isLast());

        verify(projectRepository).findByWorkspaceId(
                eq(10L),
                any(Pageable.class)
        );
    }

    @Test
    void getWorkspaceProjects_shouldThrowException_whenWorkspaceNotOwned() {

        String email = "test@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(99L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.getWorkspaceProjects(
                        99L,
                        email,
                        0,
                        10,
                        "id",
                        "asc"
                )
        );

        verify(projectRepository, never()).findByWorkspaceId(
                anyLong(),
                any(Pageable.class)
        );
    }

    @Test
    void getProject_shouldReturnProject_whenOwnedByUser() {

        String email = "test@example.com";
        Long projectId = 20L;

        Project project = new Project(
                "CodePilot API",
                "Backend development",
                workspace
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(projectRepository.findByIdAndWorkspaceUserId(projectId, 1L))
                .thenReturn(Optional.of(project));

        ProjectResponse response =
                projectService.getProject(projectId, email);

        assertNotNull(response);
        assertEquals("CodePilot API", response.name());

        verify(projectRepository)
                .findByIdAndWorkspaceUserId(projectId, 1L);
    }

    @Test
    void getProject_shouldThrowException_whenProjectNotFoundOrNotOwned() {

        String email = "test@example.com";
        Long projectId = 99L;

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(projectRepository.findByIdAndWorkspaceUserId(projectId, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.getProject(projectId, email)
        );
    }

    @Test
    void getProject_shouldThrowException_whenUserDoesNotExist() {

        String email = "missing@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> projectService.getProject(20L, email)
        );

        verify(projectRepository, never())
                .findByIdAndWorkspaceUserId(anyLong(), anyLong());
    }
}
