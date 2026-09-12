package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}