
package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.config.SecurityConfig;
import com.codepilot.springbootbasics.dto.WorkspaceResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.security.JwtAuthenticationFilter;
import com.codepilot.springbootbasics.security.JwtService;
import com.codepilot.springbootbasics.service.WorkspaceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codepilot.springbootbasics.dto.WorkspaceCreateRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

import com.codepilot.springbootbasics.exception.UserNotFoundException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(WorkspaceController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkspaceService workspaceService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CodePilotUserRepository userRepository;

    private final CodePilotUser testUser =
            new CodePilotUser(
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
    void getMyWorkspaces_shouldReturnWorkspaceList() throws Exception {

        when(workspaceService.getMyWorkspaces("test@example.com"))
                .thenReturn(List.of(
                        new WorkspaceResponse(1L, "CodePilot")
                ));

        mockMvc.perform(get("/api/workspaces")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("CodePilot"));
    }

    @Test
    void getWorkspace_shouldReturnWorkspace() throws Exception {

        when(workspaceService.getWorkspace(1L, "test@example.com"))
                .thenReturn(new WorkspaceResponse(1L, "CodePilot"));

        mockMvc.perform(get("/api/workspaces/1")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CodePilot"));
    }

    @Test
    void getWorkspaces_withoutAuthentication_shouldBeForbidden()
            throws Exception {

        mockMvc.perform(get("/api/workspaces"))
                .andExpect(status().isForbidden());
    }


    @Test
    void createWorkspace_shouldReturn201Created() throws Exception {

        when(workspaceService.createWorkspace(
                any(WorkspaceCreateRequest.class),
                org.mockito.ArgumentMatchers.eq("test@example.com")
        )).thenReturn(new WorkspaceResponse(1L, "CodePilot"));

        mockMvc.perform(post("/api/workspaces")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "CodePilot"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CodePilot"));

        verify(workspaceService).createWorkspace(
                argThat(request -> "CodePilot".equals(request.getName())),
                org.mockito.ArgumentMatchers.eq("test@example.com")
        );
    }

    @Test
    void createWorkspace_withBlankName_shouldReturn400() throws Exception {

        mockMvc.perform(post("/api/workspaces")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "   "
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString(
                                "Workspace name is required."
                        )
                ));
    }

    @Test
    void createWorkspace_withShortName_shouldReturn400() throws Exception {

        mockMvc.perform(post("/api/workspaces")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "AB"
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString(
                                "Workspace name must be between 3 and 100 characters"
                        )
                ));
    }

    @Test
    void createWorkspace_withMalformedJson_shouldReturn400() throws Exception {

        mockMvc.perform(post("/api/workspaces")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name":
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(
                        "Invalid or malformed request body."
                ));
    }


    @Test
    void updateWorkspace_shouldReturnUpdatedWorkspace() throws Exception {

        when(workspaceService.updateWorkspace(
                org.mockito.ArgumentMatchers.eq(1L),
                any(WorkspaceCreateRequest.class),
                org.mockito.ArgumentMatchers.eq("test@example.com")
        )).thenReturn(new WorkspaceResponse(1L, "Updated CodePilot"));

        mockMvc.perform(put("/api/workspaces/1")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Updated CodePilot"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated CodePilot"));
    }

    @Test
    void deleteWorkspace_shouldReturn204NoContent() throws Exception {

        mockMvc.perform(delete("/api/workspaces/1")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNoContent());

        org.mockito.Mockito.verify(workspaceService)
                .deleteWorkspace(1L, "test@example.com");
    }

    @Test
    void getWorkspace_whenMissing_shouldReturn404() throws Exception {

        when(workspaceService.getWorkspace(999L, "test@example.com"))
                .thenThrow(new UserNotFoundException("Workspace not found with id: 999"));

        mockMvc.perform(get("/api/workspaces/999")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Workspace not found with id: 999"));
    }

    @Test
    void updateWorkspace_whenMissing_shouldReturn404() throws Exception {

        when(workspaceService.updateWorkspace(
                org.mockito.ArgumentMatchers.eq(999L),
                any(WorkspaceCreateRequest.class),
                org.mockito.ArgumentMatchers.eq("test@example.com")
        )).thenThrow(new UserNotFoundException("Workspace not found with id: 999"));

        mockMvc.perform(put("/api/workspaces/999")
                        .with(authentication(testAuthentication()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Updated CodePilot"
                            }
                            """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Workspace not found with id: 999"));
    }

    @Test
    void deleteWorkspace_whenMissing_shouldReturn404() throws Exception {

        doThrow(new UserNotFoundException("Workspace not found with id: 999"))
                .when(workspaceService)
                .deleteWorkspace(999L, "test@example.com");

        mockMvc.perform(delete("/api/workspaces/999")
                        .with(authentication(testAuthentication())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Workspace not found with id: 999"));
    }


}
