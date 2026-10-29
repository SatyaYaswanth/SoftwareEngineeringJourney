
package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Task;
import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
        SELECT t FROM Task t
        WHERE t.project.id = :projectId
          AND (:status IS NULL OR t.status = :status)
          AND (:priority IS NULL OR t.priority = :priority)
        """)
    Page<Task> findByProjectWithFilters(
            @Param("projectId") Long projectId,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );

    Optional<Task> findByIdAndProject_Id(
            Long taskId,
            Long projectId
    );
}
