package com.codepilot.springbootbasics;

import com.codepilot.springbootbasics.entity.*;
import com.codepilot.springbootbasics.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class TaskRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("codepilot_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CodePilotUserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Project createProject() {
        CodePilotUser user = userRepository.save(
                new CodePilotUser(
                        "Task Test User",
                        "task-" + java.util.UUID.randomUUID() + "@test.com",
                        "password",
                        "USER"
                )
        );

        Workspace workspace = workspaceRepository.save(
                new Workspace("Task Test Workspace", user)
        );

        return projectRepository.save(
                new Project("Task Test Project", "Testing tasks", workspace)
        );
    }

    @Test
    void shouldSaveAndRetrieveTask() {
        Project project = createProject();

        Task task = taskRepository.save(
                new Task(
                        "Integration Task",
                        "Test task description",
                        TaskStatus.TODO,
                        TaskPriority.HIGH,
                        project
                )
        );

        Task found = taskRepository.findById(task.getId())
                .orElseThrow();

        assertEquals("Integration Task", found.getTitle());
        assertEquals(TaskStatus.TODO, found.getStatus());
        assertEquals(TaskPriority.HIGH, found.getPriority());
        assertEquals(project.getId(), found.getProject().getId());
    }

    @Test
    void shouldFilterTasksByStatusAndPriority() {
        Project project = createProject();

        taskRepository.save(new Task(
                "High Priority Task",
                "Urgent work",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                project
        ));

        taskRepository.save(new Task(
                "Completed Task",
                "Finished work",
                TaskStatus.COMPLETED,
                TaskPriority.HIGH,
                project
        ));

        taskRepository.save(new Task(
                "Low Priority Task",
                "Small work",
                TaskStatus.TODO,
                TaskPriority.LOW,
                project
        ));

        Page<Task> result = taskRepository.findByProjectWithFilters(
                project.getId(),
                TaskStatus.TODO,
                TaskPriority.HIGH,
                PageRequest.of(0, 10)
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("High Priority Task", result.getContent().get(0).getTitle());
    }

    @Test
    void shouldReturnTasksWithPagination() {
        Project project = createProject();

        for (int i = 1; i <= 3; i++) {
            taskRepository.save(new Task(
                    "Task " + i,
                    "Description " + i,
                    TaskStatus.TODO,
                    TaskPriority.MEDIUM,
                    project
            ));
        }

        Page<Task> result = taskRepository.findByProjectWithFilters(
                project.getId(),
                null,
                null,
                PageRequest.of(0, 2)
        );

        assertEquals(2, result.getContent().size());
        assertEquals(3, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }

    @Test
    void shouldFindTaskOnlyWithinItsProject() {
        Project project = createProject();

        Task task = taskRepository.save(new Task(
                "Project Specific Task",
                "Lookup test",
                TaskStatus.IN_PROGRESS,
                TaskPriority.MEDIUM,
                project
        ));

        assertTrue(
                taskRepository.findByIdAndProject_Id(
                        task.getId(),
                        project.getId()
                ).isPresent()
        );

        assertTrue(
                taskRepository.findByIdAndProject_Id(
                        task.getId(),
                        -1L
                ).isEmpty()
        );
    }
}