package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}