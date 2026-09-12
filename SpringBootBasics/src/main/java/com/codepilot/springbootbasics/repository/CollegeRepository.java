package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long> {
}
