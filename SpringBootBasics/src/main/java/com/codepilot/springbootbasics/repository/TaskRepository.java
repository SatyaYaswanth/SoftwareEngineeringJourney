package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}