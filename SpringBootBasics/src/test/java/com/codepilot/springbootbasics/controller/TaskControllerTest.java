
package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.config.SecurityConfig;
import com.codepilot.springbootbasics.dto.TaskCreateRequest;
import com.codepilot.springbootbasics.dto.TaskResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.exception.GlobalExceptionHandler;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.security.JwtAuthenticationFilter;
import com.codepilot.springbootbasics.security.JwtService;
import com.codepilot.springbootbasics.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({
        SecurityConfig.class,
        JwtAuthenticationFilter.class,
        GlobalExceptionHandler.class
})
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CodePilotUserRepository codePilotUserRepository;

    private final CodePilotUser testUser = new CodePilotUser(
            "Test User",
            "test@example.com",
            "hashed-password",
            "USER"
    );

    private Authentication testAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                testUser,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    private TaskResponse sampleTask() {
        return new TaskResponse(
                101L,
                "Fix bug",
                "Investigate issue",
                null,
                null,
                10L
        );
    }

    private String validTaskJson() {
        return """
                {
                    "title": "Fix bug",
                    "description": "Investigate issue"
                }
                """;
    }

    @Test
    void createTaskReturnsCreated() throws Exception {
        when(taskService.createTask(
                eq(10L),
                any(TaskCreateRequest.class),
                eq("test@example.com")
        )).thenReturn(sampleTask());

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTaskJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.title").value("Fix bug"))
                .andExpect(jsonPath("$.description").value("Investigate issue"))
                .andExpect(jsonPath("$.projectId").value(10));

        verify(taskService).createTask(
                eq(10L),
                any(TaskCreateRequest.class),
                eq("test@example.com")
        );
    }

    @Test
    void getProjectTasksReturnsTaskList() throws Exception {
        when(taskService.getProjectTasks(10L, "test@example.com"))
                .thenReturn(List.of(sampleTask()));

        mockMvc.perform(get("/api/projects/10/tasks")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].title").value("Fix bug"))
                .andExpect(jsonPath("$[0].projectId").value(10));

        verify(taskService).getProjectTasks(10L, "test@example.com");
    }

    @Test
    void getTaskReturnsTask() throws Exception {
        when(taskService.getTask(10L, 101L, "test@example.com"))
                .thenReturn(sampleTask());

        mockMvc.perform(get("/api/projects/10/tasks/101")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.title").value("Fix bug"))
                .andExpect(jsonPath("$.projectId").value(10));

        verify(taskService).getTask(10L, 101L, "test@example.com");
    }

    @Test
    void updateTaskReturnsUpdatedTask() throws Exception {
        when(taskService.updateTask(
                eq(10L),
                eq(101L),
                any(TaskCreateRequest.class),
                eq("test@example.com")
        )).thenReturn(sampleTask());

        mockMvc.perform(put("/api/projects/10/tasks/101")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTaskJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.title").value("Fix bug"))
                .andExpect(jsonPath("$.projectId").value(10));

        verify(taskService).updateTask(
                eq(10L),
                eq(101L),
                any(TaskCreateRequest.class),
                eq("test@example.com")
        );
    }

    @Test
    void deleteTaskReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/projects/10/tasks/101")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(10L, 101L, "test@example.com");
    }

    @Test
    void createTaskWithBlankTitleReturnsBadRequest() throws Exception {
        String json = """
                {
                    "title": "   ",
                    "description": "Invalid task"
                }
                """;

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTaskWithTitleExceedingLimitReturnsBadRequest()
            throws Exception {
        String longTitle = "a".repeat(151);
        String json = """
                {
                    "title": "%s",
                    "description": "Task description"
                }
                """.formatted(longTitle);

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTaskWithDescriptionExceedingLimitReturnsBadRequest()
            throws Exception {
        String longDescription = "a".repeat(1001);
        String json = """
                {
                    "title": "Valid task",
                    "description": "%s"
                }
                """.formatted(longDescription);

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTaskWithInvalidStatusReturnsBadRequest() throws Exception {
        String json = """
                {
                    "title": "Valid task",
                    "description": "Description",
                    "status": "NOT_A_VALID_STATUS"
                }
                """;

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTaskWithInvalidPriorityReturnsBadRequest() throws Exception {
        String json = """
                {
                    "title": "Valid task",
                    "description": "Description",
                    "priority": "NOT_A_VALID_PRIORITY"
                }
                """;

        mockMvc.perform(post("/api/projects/10/tasks")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unauthenticatedRequestIsRejected() throws Exception {
        mockMvc.perform(get("/api/projects/10/tasks/101"))
                .andExpect(status().isForbidden());
    }
}
