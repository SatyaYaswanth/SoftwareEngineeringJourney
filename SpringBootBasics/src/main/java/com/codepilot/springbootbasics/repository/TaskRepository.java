
package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject_Id(Long projectId);

    Optional<Task> findByIdAndProject_Id(
            Long taskId,
            Long projectId
    );
}
