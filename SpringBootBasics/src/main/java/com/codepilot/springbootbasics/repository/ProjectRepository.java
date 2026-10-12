
package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByWorkspaceId(Long workspaceId);

    Optional<Project> findByIdAndWorkspaceUserId(
            Long projectId,
            Long userId
    );
}
