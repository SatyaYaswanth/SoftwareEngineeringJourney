package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
