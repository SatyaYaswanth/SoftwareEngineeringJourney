
package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.config.SecurityConfig;
import com.codepilot.springbootbasics.dto.PageResponse;
import com.codepilot.springbootbasics.dto.ProjectCreateRequest;
import com.codepilot.springbootbasics.dto.ProjectResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.exception.GlobalExceptionHandler;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.security.JwtAuthenticationFilter;
import com.codepilot.springbootbasics.security.JwtService;
import com.codepilot.springbootbasics.service.ProjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@Import({
        SecurityConfig.class,
        JwtAuthenticationFilter.class,
        GlobalExceptionHandler.class
})
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CodePilotUserRepository userRepository;

    private final CodePilotUser testUser = new CodePilotUser(
            "Test User",
            "test@example.com",
            "hashed-password",
            "USER"
    );

    private UsernamePasswordAuthenticationToken testAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                testUser,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void createProject_shouldReturn201Created() throws Exception {

        when(projectService.createProject(
                eq(10L),
                any(ProjectCreateRequest.class),
                eq("test@example.com")
        )).thenReturn(new ProjectResponse(
                20L,
                "CodePilot API",
                "Backend development",
                10L
        ));

        mockMvc.perform(post("/api/workspaces/10/projects")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "CodePilot API",
                                    "description": "Backend development"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(20))
                .andExpect(jsonPath("$.name").value("CodePilot API"))
                .andExpect(jsonPath("$.description")
                        .value("Backend development"))
                .andExpect(jsonPath("$.workspaceId").value(10));

        verify(projectService).createProject(
                eq(10L),
                any(ProjectCreateRequest.class),
                eq("test@example.com")
        );
    }

    @Test
    void createProject_withBlankName_shouldReturn400() throws Exception {

        mockMvc.perform(post("/api/workspaces/10/projects")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "   ",
                                    "description": "Backend development"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value(org.hamcrest.Matchers.containsString(
                                "Project name is required"
                        )));
    }

    @Test
    void createProject_withNameExceeding100Characters_shouldReturn400()
            throws Exception {

        String longName = "A".repeat(101);

        mockMvc.perform(post("/api/workspaces/10/projects")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "%s",
                                    "description": "Backend development"
                                }
                                """.formatted(longName)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value(org.hamcrest.Matchers.containsString(
                                "Project name cannot exceed 100 characters"
                        )));
    }

    @Test
    void getWorkspaceProjects_shouldReturnPaginatedResponse()
            throws Exception {

        when(projectService.getWorkspaceProjects(
                10L,
                "test@example.com",
                0,
                10,
                "id",
                "asc"
        )).thenReturn(new PageResponse<>(
                List.of(
                        new ProjectResponse(
                                20L, "Frontend", "User interface", 10L
                        ),
                        new ProjectResponse(
                                21L, "Backend", "REST API", 10L
                        )
                ),
                0,
                10,
                2,
                1,
                true,
                true
        ));

        mockMvc.perform(get("/api/workspaces/10/projects")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Frontend"))
                .andExpect(jsonPath("$.content[1].name").value("Backend"))
                .andExpect(jsonPath("$.content[0].workspaceId").value(10))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));

        verify(projectService).getWorkspaceProjects(
                10L,
                "test@example.com",
                0,
                10,
                "id",
                "asc"
        );
    }

    @Test
    void getWorkspaceProjects_whenWorkspaceMissing_shouldReturn404()
            throws Exception {

        when(projectService.getWorkspaceProjects(
                999L,
                "test@example.com",
                0,
                10,
                "id",
                "asc"
        )).thenThrow(new UserNotFoundException(
                "Workspace not found with id: 999"
        ));

        mockMvc.perform(get("/api/workspaces/999/projects")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Workspace not found with id: 999"));
    }

    @Test
    void getProject_shouldReturnProject() throws Exception {

        when(projectService.getProject(20L, "test@example.com"))
                .thenReturn(new ProjectResponse(
                        20L,
                        "CodePilot API",
                        "Backend development",
                        10L
                ));

        mockMvc.perform(get("/api/projects/20")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(20))
                .andExpect(jsonPath("$.name").value("CodePilot API"))
                .andExpect(jsonPath("$.description")
                        .value("Backend development"))
                .andExpect(jsonPath("$.workspaceId").value(10));
    }

    @Test
    void getProject_whenMissing_shouldReturn404() throws Exception {

        when(projectService.getProject(999L, "test@example.com"))
                .thenThrow(new UserNotFoundException(
                        "Project not found with id: 999"
                ));

        mockMvc.perform(get("/api/projects/999")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Project not found with id: 999"));
    }

    @Test
    void getProject_withoutAuthentication_shouldBeRejected()
            throws Exception {

        mockMvc.perform(get("/api/projects/20"))
                .andExpect(status().isForbidden());
    }
}
