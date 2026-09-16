package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.dto.PageResponse;
import com.codepilot.springbootbasics.dto.StudentCreateRequest;
import com.codepilot.springbootbasics.dto.StudentResponse;
import com.codepilot.springbootbasics.entity.Student;
import com.codepilot.springbootbasics.service.StudentRelationshipService;
import com.codepilot.springbootbasics.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRelationshipService studentRelationshipService;

    public StudentController(
            StudentService studentService,
            StudentRelationshipService studentRelationshipService) {

        this.studentService = studentService;
        this.studentRelationshipService = studentRelationshipService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentCreateRequest request) {

        StudentResponse response = studentService.createStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id) {

        StudentResponse student = studentService.findById(id);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/courses")
    public Student getStudentWithCourses(@PathVariable Long id) {
        return studentRelationshipService.getStudentWithCourses(id);
    }

    @GetMapping("/search/advanced")
    public List<Student> searchByNameAndEmail(
            @RequestParam String name,
            @RequestParam String email) {

        return studentService.findStudentsByNameAndEmail(name, email);
    }

    @GetMapping("/search/custom")
    public List<Student> searchByNameCustom(
            @RequestParam String name) {

        return studentService.searchByNameCustom(name);
    }

    @GetMapping("/search")
    public PageResponse<StudentResponse> searchStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0) {
            throw new IllegalArgumentException("Page cannot be negative");
        }

        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException(
                    "Size must be between 1 and 100"
            );
        }

        return studentService.searchStudents(
                keyword,
                page,
                size,
                sortBy,
                direction
        );
    }
}
