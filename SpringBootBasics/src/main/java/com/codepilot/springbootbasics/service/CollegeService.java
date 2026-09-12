package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.entity.College;
import com.codepilot.springbootbasics.repository.CollegeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollegeService {
    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Transactional
    public void printCollegeStudents() {

        College college = collegeRepository.findById(1L)
                .orElseThrow();

        System.out.println("College: " + college.getName());

        for (var student : college.getStudents()) {
            System.out.println(
                    "Student: " + student.getName()
                            + " | Email: " + student.getEmail()
            );
        }
    }

}
