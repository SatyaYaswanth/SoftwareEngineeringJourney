package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
