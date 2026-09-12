package com.codepilot.springbootbasics.controller;

import com.codepilot.springbootbasics.entity.College;
import com.codepilot.springbootbasics.repository.CollegeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colleges")
public class CollegeController {
    private final CollegeRepository collegeRepository;


    public CollegeController(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @GetMapping("/{id}")
    public College getCollege(@PathVariable Long id) {
        return collegeRepository.findById(id)
                .orElseThrow();
    }
}
