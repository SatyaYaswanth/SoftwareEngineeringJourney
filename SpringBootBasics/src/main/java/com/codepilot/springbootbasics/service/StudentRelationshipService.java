package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.entity.Course;
import com.codepilot.springbootbasics.entity.Student;
import com.codepilot.springbootbasics.repository.CourseRepository;
import com.codepilot.springbootbasics.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentRelationshipService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentRelationshipService(StudentRepository studentRepository,
                                      CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void enrollStudentInCourses() {

        Course java = new Course();
        java.setName("Java");

        Course springBoot = new Course();
        springBoot.setName("Spring Boot");

        courseRepository.save(java);
        courseRepository.save(springBoot);

        Student student = studentRepository.findById(1L)
                .orElseThrow();

        student.getCourses().add(java);
        student.getCourses().add(springBoot);

        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student getStudentWithCourses(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow();

        // Access the lazy collection while transaction is active
        student.getCourses().size();

        return student;
    }


}