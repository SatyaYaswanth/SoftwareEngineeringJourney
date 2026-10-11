package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository
        extends JpaRepository<Workspace, Long> {

    List<Workspace> findByUserId(Long userId);

    Optional<Workspace> findByIdAndUserId(
            Long workspaceId,
            Long userId
    );
}