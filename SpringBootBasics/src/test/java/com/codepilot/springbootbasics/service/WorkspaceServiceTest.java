
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.WorkspaceCreateRequest;
import com.codepilot.springbootbasics.dto.WorkspaceResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Workspace;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private CodePilotUserRepository codePilotUserRepository;

    @InjectMocks
    private WorkspaceService workspaceService;

    private CodePilotUser user;

    @BeforeEach
    void setUp() throws Exception {
        user = new CodePilotUser(
                "Test User",
                "test@example.com",
                "hashed-password",
                "USER"
        );

        setGeneratedId(user, 1L);
    }

    // Assign generated IDs in test fixtures without adding setters
    // to production entities.
    private void setGeneratedId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    void createWorkspace_shouldCreateWorkspace_whenUserExists()
            throws Exception {

        String email = "test@example.com";

        WorkspaceCreateRequest request = new WorkspaceCreateRequest();
        request.setName("My Workspace");

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.save(any(Workspace.class)))
                .thenAnswer(invocation -> {
                    Workspace workspace = invocation.getArgument(0);
                    setGeneratedId(workspace, 10L);
                    return workspace;
                });

        WorkspaceResponse response =
                workspaceService.createWorkspace(request, email);

        assertEquals(10L, response.getId());
        assertEquals("My Workspace", response.getName());

        verify(codePilotUserRepository).findByEmail(email);
        verify(workspaceRepository).save(any(Workspace.class));
    }

    @Test
    void createWorkspace_shouldThrowException_whenUserDoesNotExist() {
        String email = "missing@example.com";

        WorkspaceCreateRequest request = new WorkspaceCreateRequest();
        request.setName("My Workspace");

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.createWorkspace(request, email)
        );

        verify(workspaceRepository, never()).save(any(Workspace.class));
    }

    @Test
    void getMyWorkspaces_shouldReturnWorkspaces_whenUserExists()
            throws Exception {

        String email = "test@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        Workspace first = new Workspace("Workspace One", user);
        setGeneratedId(first, 10L);

        Workspace second = new Workspace("Workspace Two", user);
        setGeneratedId(second, 11L);

        when(workspaceRepository.findByUserId(1L))
                .thenReturn(List.of(first, second));

        List<WorkspaceResponse> responses =
                workspaceService.getMyWorkspaces(email);

        assertEquals(2, responses.size());
        assertEquals(10L, responses.get(0).getId());
        assertEquals("Workspace One", responses.get(0).getName());
        assertEquals(11L, responses.get(1).getId());
        assertEquals("Workspace Two", responses.get(1).getName());

        verify(codePilotUserRepository).findByEmail(email);
        verify(workspaceRepository).findByUserId(1L);
    }

    @Test
    void getMyWorkspaces_shouldThrowException_whenUserDoesNotExist() {
        String email = "missing@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.getMyWorkspaces(email)
        );

        verify(workspaceRepository, never()).findByUserId(anyLong());
    }

    @Test
    void getWorkspace_shouldReturnWorkspace_whenOwnedByUser() {
        String email = "test@example.com";
        Long workspaceId = 10L;

        Workspace workspace = new Workspace("My Workspace", user);

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.of(workspace));

        WorkspaceResponse response =
                workspaceService.getWorkspace(workspaceId, email);

        assertEquals("My Workspace", response.getName());

        verify(workspaceRepository)
                .findByIdAndUserId(workspaceId, 1L);
    }

    @Test
    void getWorkspace_shouldThrowException_whenWorkspaceNotFound() {
        String email = "test@example.com";
        Long workspaceId = 99L;

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.getWorkspace(workspaceId, email)
        );
    }

    @Test
    void getWorkspace_shouldThrowException_whenUserDoesNotExist() {
        String email = "missing@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.getWorkspace(10L, email)
        );

        verify(workspaceRepository, never())
                .findByIdAndUserId(anyLong(), anyLong());
    }

    @Test
    void updateWorkspace_shouldUpdateName_whenWorkspaceBelongsToUser() {
        String email = "test@example.com";
        Long workspaceId = 10L;

        Workspace workspace = new Workspace("Old Name", user);

        WorkspaceCreateRequest request = new WorkspaceCreateRequest();
        request.setName("Updated Name");

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.of(workspace));

        when(workspaceRepository.save(workspace))
                .thenReturn(workspace);

        WorkspaceResponse response =
                workspaceService.updateWorkspace(workspaceId, request, email);

        assertEquals("Updated Name", response.getName());

        verify(workspaceRepository).save(workspace);
    }

    @Test
    void updateWorkspace_shouldThrowException_whenWorkspaceNotFound() {
        String email = "test@example.com";
        Long workspaceId = 99L;

        WorkspaceCreateRequest request = new WorkspaceCreateRequest();
        request.setName("Updated Name");

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.updateWorkspace(
                        workspaceId, request, email)
        );

        verify(workspaceRepository, never()).save(any(Workspace.class));
    }

    @Test
    void deleteWorkspace_shouldDeleteWorkspace_whenOwnedByUser() {
        String email = "test@example.com";
        Long workspaceId = 10L;

        Workspace workspace = new Workspace("My Workspace", user);

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.of(workspace));

        workspaceService.deleteWorkspace(workspaceId, email);

        verify(workspaceRepository).delete(workspace);
    }

    @Test
    void deleteWorkspace_shouldThrowException_whenWorkspaceNotFound() {
        String email = "test@example.com";
        Long workspaceId = 99L;

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(workspaceRepository.findByIdAndUserId(workspaceId, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> workspaceService.deleteWorkspace(workspaceId, email)
        );

        verify(workspaceRepository, never()).delete(any(Workspace.class));
    }


}
