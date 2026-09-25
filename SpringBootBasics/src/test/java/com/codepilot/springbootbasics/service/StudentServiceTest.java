package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.StudentCreateRequest;
import com.codepilot.springbootbasics.dto.StudentResponse;
import com.codepilot.springbootbasics.entity.Student;
import com.codepilot.springbootbasics.exception.StudentNotFoundException;
import com.codepilot.springbootbasics.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;


    // 1. Find student - success
    @Test
    void findById_shouldReturnStudent_whenStudentExists() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Rahul");
        student.setEmail("rahul@example.com");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        StudentResponse response = studentService.findById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("rahul@example.com", response.getEmail());

        verify(studentRepository).findById(1L);
    }


    // 2. Find student - not found
    @Test
    void findById_shouldThrowException_whenStudentDoesNotExist() {

        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> studentService.findById(999L)
        );

        assertEquals(
                "Student with id 999 not found",
                exception.getMessage()
        );

        verify(studentRepository).findById(999L);
    }


    // 3. Create student
    @Test
    void createStudent_shouldReturnStudentResponse_whenStudentIsCreated() {

        StudentCreateRequest request =
                new StudentCreateRequest(
                        "Rahul",
                        "rahul@example.com"
                );

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Rahul");
        savedStudent.setEmail("rahul@example.com");

        when(studentRepository.save(any(Student.class)))
                .thenReturn(savedStudent);

        StudentResponse response =
                studentService.createStudent(request);

        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("rahul@example.com", response.getEmail());

        verify(studentRepository).save(any(Student.class));
    }


    // 4. Get all students
    @Test
    void getAllStudents_shouldReturnAllStudents() {

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Rahul");
        student1.setEmail("rahul@example.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Priya");
        student2.setEmail("priya@example.com");

        when(studentRepository.findAll())
                .thenReturn(List.of(student1, student2));

        List<StudentResponse> response =
                studentService.getAllStudents();

        assertEquals(2, response.size());
        assertEquals("Rahul", response.get(0).getName());
        assertEquals("Priya", response.get(1).getName());

        verify(studentRepository).findAll();
    }


    // 5. Delete student - success
    @Test
    void deleteStudent_shouldDeleteStudent_whenStudentExists() {

        when(studentRepository.existsById(1L))
                .thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository).existsById(1L);
        verify(studentRepository).deleteById(1L);
    }


    // 6. Delete student - not found
    @Test
    void deleteStudent_shouldThrowException_whenStudentDoesNotExist() {

        when(studentRepository.existsById(999L))
                .thenReturn(false);

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> studentService.deleteStudent(999L)
        );

        assertEquals(
                "Student with id 999 not found",
                exception.getMessage()
        );

        verify(studentRepository).existsById(999L);
        verify(studentRepository, never()).deleteById(999L);
    }
}