package com.codepilot.springbootbasics;

import com.codepilot.springbootbasics.entity.Student;
import com.codepilot.springbootbasics.repository.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class StudentIntegrationTest {

    @BeforeAll
    static void setTimezone() {
        java.util.TimeZone.setDefault(
                java.util.TimeZone.getTimeZone("Asia/Kolkata")
        );
    }

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("codepilot_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {

        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
        );

        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );

        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );
    }

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldSaveAndRetrieveStudent() {

        Student student = new Student();

        student.setName("Integration Test");
        student.setEmail("integration@test.com");

        Student savedStudent =
                studentRepository.save(student);

        assertNotNull(savedStudent.getId());

        Student foundStudent =
                studentRepository.findById(savedStudent.getId())
                        .orElseThrow();

        assertEquals(
                "Integration Test",
                foundStudent.getName()
        );

        assertEquals(
                "integration@test.com",
                foundStudent.getEmail()
        );
    }
}