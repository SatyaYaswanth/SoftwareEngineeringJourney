package com.codepilot.springbootbasics;

import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Project;
import com.codepilot.springbootbasics.entity.Workspace;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.ProjectRepository;
import com.codepilot.springbootbasics.repository.WorkspaceRepository;
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
class ProjectRepositoryIntegrationTest {

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

    private Workspace createWorkspace() {
        CodePilotUser user = userRepository.save(
                new CodePilotUser(
                        "Integration User",
                        "project-" + java.util.UUID.randomUUID() + "@test.com",
                        "password",
                        "USER"
                )
        );

        return workspaceRepository.save(
                new Workspace("Integration Workspace", user)
        );
    }

    @Test
    void shouldSaveAndRetrieveProject() {
        Workspace workspace = createWorkspace();

        Project project = projectRepository.save(
                new Project("Integration Project", "Test description", workspace)
        );

        Project found = projectRepository.findById(project.getId())
                .orElseThrow();

        assertEquals("Integration Project", found.getName());
        assertEquals("Test description", found.getDescription());
        assertEquals(workspace.getId(), found.getWorkspace().getId());
    }

    @Test
    void shouldReturnProjectsWithPagination() {
        Workspace workspace = createWorkspace();

        projectRepository.save(
                new Project("Project One", "First", workspace)
        );
        projectRepository.save(
                new Project("Project Two", "Second", workspace)
        );
        projectRepository.save(
                new Project("Project Three", "Third", workspace)
        );

        Page<Project> result = projectRepository.findByWorkspaceId(
                workspace.getId(),
                PageRequest.of(0, 2)
        );

        assertEquals(2, result.getContent().size());
        assertEquals(3, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }

    @Test
    void shouldFindProjectByIdAndWorkspaceOwner() {
        Workspace workspace = createWorkspace();

        Project project = projectRepository.save(
                new Project("Owned Project", "Owner test", workspace)
        );

        assertTrue(
                projectRepository.findByIdAndWorkspaceUserId(
                        project.getId(),
                        workspace.getUser().getId()
                ).isPresent()
        );

        assertTrue(
                projectRepository.findByIdAndWorkspaceUserId(
                        project.getId(),
                        -1L
                ).isEmpty()
        );
    }
}