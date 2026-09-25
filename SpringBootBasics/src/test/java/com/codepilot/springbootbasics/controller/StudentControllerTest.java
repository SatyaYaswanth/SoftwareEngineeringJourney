package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.StudentCreateRequest;
import com.codepilot.springbootbasics.dto.StudentResponse;
import com.codepilot.springbootbasics.service.StudentRelationshipService;
import com.codepilot.springbootbasics.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private StudentRelationshipService studentRelationshipService;

    @Test
    void createStudent_shouldReturn201_whenRequestIsValid() throws Exception {

        StudentResponse response =
                new StudentResponse(
                        1L,
                        "Rahul",
                        "rahul@example.com"
                );

        when(studentService.createStudent(any(StudentCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/students")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "Rahul",
                                    "email": "rahul@example.com"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.email").value("rahul@example.com"));
    }

    @Test
    void createStudent_shouldReturn400_whenRequestIsInvalid() throws Exception {

        mockMvc.perform(
                        post("/api/students")
                                .contentType(APPLICATION_JSON)
                                .content("""
                            {
                                "name": "",
                                "email": "invalid"
                            }
                            """)
                )
                .andExpect(status().isBadRequest());

        verify(studentService, never())
                .createStudent(any(StudentCreateRequest.class));
    }

    @Test
    void getStudentById_shouldReturnStudent_whenStudentExists() throws Exception {

        StudentResponse response =
                new StudentResponse(
                        1L,
                        "Rahul",
                        "rahul@example.com"
                );

        when(studentService.findById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/students/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.email").value("rahul@example.com"));

        verify(studentService).findById(1L);
    }
}