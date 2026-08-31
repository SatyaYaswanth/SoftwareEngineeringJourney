package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.StudentCreateRequest;
import com.codepilot.springbootbasics.dto.StudentResponse;
import com.codepilot.springbootbasics.exception.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentService {
    private final List<StudentResponse> students = new ArrayList<>();

    private Long nextId = 1L;

    public StudentResponse createStudent(StudentCreateRequest request){
        StudentResponse student = new StudentResponse(nextId++, request.getName(), request.getEmail());
        students.add(student);
        return student;

    }

    public List<StudentResponse> getAllStudents() {
        return students;
    }

    public StudentResponse findById(Long id){
        for(StudentResponse student: students){
            if (student.getId().equals(id)) {
                return student;
            }
        }
        throw new StudentNotFoundException("Student with id " + id + " not found");
    }

    public void deleteStudent(Long id){
        Iterator<StudentResponse> iterator = students.iterator();

        while(students.iterator().hasNext()){
            StudentResponse student = iterator.next();
            if(student.getId().equals(id)){
                iterator.remove();
                return;
            }
        }
        throw new StudentNotFoundException(
                "Student with id " + id + " not found"
        );
    }
}
