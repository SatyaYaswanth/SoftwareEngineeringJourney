
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.TaskCreateRequest;
import com.codepilot.springbootbasics.dto.TaskResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Project;
import com.codepilot.springbootbasics.entity.Task;
import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.ProjectRepository;
import com.codepilot.springbootbasics.repository.TaskRepository;
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
import static org.mockito.Mockito.*;
import com.codepilot.springbootbasics.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CodePilotUserRepository codePilotUserRepository;

    @InjectMocks
    private TaskService taskService;

    private CodePilotUser user;
    private Project project;

    @BeforeEach
    void setUp() throws Exception {
        user = new CodePilotUser(
                "Test User",
                "test@example.com",
                "hashed-password",
                "USER"
        );

        setGeneratedId(user, 1L);

        project = new Project(
                "CodePilot API",
                "Backend development",
                null
        );

        setGeneratedId(project, 20L);
    }

    // Test fixture helper; production entities remain unchanged.
    private void setGeneratedId(Object entity, Long id)
            throws Exception {
        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    private void authorizeProject(Long projectId, String email) {
        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(projectRepository.findByIdAndWorkspaceUserId(
                projectId, 1L))
                .thenReturn(Optional.of(project));
    }

    @Test
    void createTask_shouldCreateTask_whenProjectIsAuthorized() {
        String email = "test@example.com";
        Long projectId = 20L;

        TaskCreateRequest request = new TaskCreateRequest(
                "Implement login",
                "Create JWT login endpoint",
                TaskStatus.IN_PROGRESS,
                TaskPriority.HIGH
        );

        authorizeProject(projectId, email);

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response =
                taskService.createTask(projectId, request, email);

        assertNotNull(response);
        assertEquals("Implement login", response.title());
        assertEquals(TaskStatus.IN_PROGRESS, response.status());
        assertEquals(TaskPriority.HIGH, response.priority());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_shouldUseDefaults_whenStatusAndPriorityAreNull() {
        String email = "test@example.com";
        Long projectId = 20L;

        TaskCreateRequest request = new TaskCreateRequest(
                "Write tests",
                "Add unit tests",
                null,
                null
        );

        authorizeProject(projectId, email);

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response =
                taskService.createTask(projectId, request, email);

        assertEquals(TaskStatus.TODO, response.status());
        assertEquals(TaskPriority.MEDIUM, response.priority());
    }

    @Test
    void createTask_shouldThrowException_whenUserDoesNotExist() {
        String email = "missing@example.com";

        TaskCreateRequest request = new TaskCreateRequest(
                "Task",
                "Description",
                null,
                null
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> taskService.createTask(20L, request, email)
        );

        verify(projectRepository, never())
                .findByIdAndWorkspaceUserId(anyLong(), anyLong());

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void createTask_shouldThrowException_whenProjectIsNotOwnedByUser() {
        String email = "test@example.com";

        TaskCreateRequest request = new TaskCreateRequest(
                "Task",
                "Description",
                null,
                null
        );

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(projectRepository.findByIdAndWorkspaceUserId(20L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.createTask(20L, request, email)
        );

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getProjectTasks_shouldReturnTasks_whenProjectIsAuthorized() {
        String email = "test@example.com";

        Task first = new Task(
                "Task One",
                "First task",
                TaskStatus.TODO,
                TaskPriority.MEDIUM,
                project
        );

        Task second = new Task(
                "Task Two",
                "Second task",
                TaskStatus.COMPLETED,
                TaskPriority.HIGH,
                project
        );

        authorizeProject(20L, email);

        when(taskRepository.findAllByProject_Id(20L))
                .thenReturn(List.of(first, second));

        List<TaskResponse> responses =
                taskService.getProjectTasks(20L, email);

        assertEquals(2, responses.size());
        assertEquals("Task One", responses.get(0).title());
        assertEquals("Task Two", responses.get(1).title());

        verify(taskRepository).findAllByProject_Id(20L);
    }

    @Test
    void getProjectTasks_shouldThrowException_whenProjectIsNotAuthorized() {
        String email = "test@example.com";

        when(codePilotUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(projectRepository.findByIdAndWorkspaceUserId(20L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.getProjectTasks(20L, email)
        );

        verify(taskRepository, never()).findAllByProject_Id(anyLong());
    }

    @Test
    void getTask_shouldReturnTask_whenTaskBelongsToProject() {
        String email = "test@example.com";

        Task task = new Task(
                "Implement login",
                "Create login endpoint",
                TaskStatus.IN_PROGRESS,
                TaskPriority.HIGH,
                project
        );

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(30L, 20L))
                .thenReturn(Optional.of(task));

        TaskResponse response =
                taskService.getTask(20L, 30L, email);

        assertEquals("Implement login", response.title());
        assertEquals(TaskStatus.IN_PROGRESS, response.status());
    }

    @Test
    void getTask_shouldThrowException_whenTaskIsNotInProject() {
        String email = "test@example.com";

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(99L, 20L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.getTask(20L, 99L, email)
        );
    }

    @Test
    void updateTask_shouldUpdateTask_whenTaskBelongsToProject() {
        String email = "test@example.com";

        Task task = new Task(
                "Old title",
                "Old description",
                TaskStatus.TODO,
                TaskPriority.LOW,
                project
        );

        TaskCreateRequest request = new TaskCreateRequest(
                "Updated title",
                "Updated description",
                TaskStatus.COMPLETED,
                TaskPriority.HIGH
        );

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(30L, 20L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task)).thenReturn(task);

        TaskResponse response =
                taskService.updateTask(20L, 30L, request, email);

        assertEquals("Updated title", response.title());
        assertEquals("Updated description", response.description());
        assertEquals(TaskStatus.COMPLETED, response.status());
        assertEquals(TaskPriority.HIGH, response.priority());

        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_shouldThrowException_whenTaskIsNotInProject() {
        String email = "test@example.com";

        TaskCreateRequest request = new TaskCreateRequest(
                "Updated title",
                "Updated description",
                TaskStatus.COMPLETED,
                TaskPriority.HIGH
        );

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(99L, 20L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.updateTask(20L, 99L, request, email)
        );

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_shouldDeleteTask_whenTaskBelongsToProject() {
        String email = "test@example.com";

        Task task = new Task(
                "Task to delete",
                "Delete this task",
                TaskStatus.TODO,
                TaskPriority.MEDIUM,
                project
        );

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(30L, 20L))
                .thenReturn(Optional.of(task));

        taskService.deleteTask(20L, 30L, email);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_shouldThrowException_whenTaskIsNotInProject() {
        String email = "test@example.com";

        authorizeProject(20L, email);

        when(taskRepository.findByIdAndProject_Id(99L, 20L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deleteTask(20L, 99L, email)
        );

        verify(taskRepository, never()).delete(any(Task.class));
    }
}
