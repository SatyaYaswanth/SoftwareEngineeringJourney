
package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByWorkspaceId(
            Long workspaceId,
            Pageable pageable
    );

    Optional<Project> findByIdAndWorkspaceUserId(
            Long projectId,
            Long userId
    );
}
