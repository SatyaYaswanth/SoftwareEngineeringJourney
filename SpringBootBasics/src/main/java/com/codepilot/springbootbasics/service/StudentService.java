package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.PageResponse;
import com.codepilot.springbootbasics.dto.StudentCreateRequest;
import com.codepilot.springbootbasics.dto.StudentResponse;
import com.codepilot.springbootbasics.entity.Student;
import com.codepilot.springbootbasics.exception.StudentNotFoundException;
import com.codepilot.springbootbasics.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse createStudent(StudentCreateRequest request) {

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());

        Student savedStudent = studentRepository.save(student);

        return new StudentResponse(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail()
        );
    }

    public List<StudentResponse> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail()
                ))
                .toList();
    }

    public StudentResponse findById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with id " + id + " not found"
                        ));

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail()
        );
    }

    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(
                    "Student with id " + id + " not found"
            );
        }

        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }


    public List<Student> findStudentsByNameAndEmail(String name, String email) {
        return studentRepository.findByNameAndEmail(name, email);
    }

    public List<Student> searchByNameCustom(String name) {
        return studentRepository.searchByName(name);
    }

    public List<Student> searchStudents(
            String keyword,
            String sortBy,
            String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        return studentRepository
                .findByNameContainingIgnoreCase(keyword, sort);
    }

    public PageResponse<StudentResponse> searchStudents(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else if (direction.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            throw new IllegalArgumentException("Direction must be 'asc' or 'desc'");
        }

        if (!sortBy.equals("id")
                && !sortBy.equals("name")
                && !sortBy.equals("email")) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentPage =
                studentRepository.findByNameContainingIgnoreCase(keyword, pageable);

        Page<StudentResponse> studentResponsePage = studentPage.map(student ->
                new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail()
                )
        );

        return new PageResponse<>(
                studentResponsePage.getContent(),
                studentResponsePage.getNumber(),
                studentResponsePage.getSize(),
                studentResponsePage.getTotalElements(),
                studentResponsePage.getTotalPages(),
                studentResponsePage.isFirst(),
                studentResponsePage.isLast()
        );
    }
}
