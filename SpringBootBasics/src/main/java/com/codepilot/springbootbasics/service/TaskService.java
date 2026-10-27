
package com.codepilot.springbootbasics.service;

import com.codepilot.springbootbasics.dto.TaskCreateRequest;
import com.codepilot.springbootbasics.dto.TaskResponse;
import com.codepilot.springbootbasics.entity.CodePilotUser;
import com.codepilot.springbootbasics.entity.Project;
import com.codepilot.springbootbasics.entity.Task;
import com.codepilot.springbootbasics.entity.TaskPriority;
import com.codepilot.springbootbasics.entity.TaskStatus;
import com.codepilot.springbootbasics.exception.UserNotFoundException;
import com.codepilot.springbootbasics.repository.CodePilotUserRepository;
import com.codepilot.springbootbasics.repository.ProjectRepository;
import com.codepilot.springbootbasics.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.codepilot.springbootbasics.exception.ResourceNotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final CodePilotUserRepository codePilotUserRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            CodePilotUserRepository codePilotUserRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.codePilotUserRepository = codePilotUserRepository;
    }

    private Project getAuthorizedProject(
            Long projectId,
            String authenticatedEmail) {

        CodePilotUser user = codePilotUserRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"));

        return projectRepository
                .findByIdAndWorkspaceUserId(
                        projectId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));
    }

    @Transactional
    public TaskResponse createTask(
            Long projectId,
            TaskCreateRequest request,
            String authenticatedEmail) {

        Project project = getAuthorizedProject(
                projectId, authenticatedEmail);

        TaskStatus status = request.status() != null
                ? request.status()
                : TaskStatus.TODO;

        TaskPriority priority = request.priority() != null
                ? request.priority()
                : TaskPriority.MEDIUM;

        Task task = new Task(
                request.title(),
                request.description(),
                status,
                priority,
                project
        );

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getProjectTasks(
            Long projectId,
            String authenticatedEmail) {

        Project project = getAuthorizedProject(
                projectId, authenticatedEmail);

        return taskRepository
                .findAllByProject_Id(project.getId())
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(
            Long projectId,
            Long taskId,
            String authenticatedEmail) {

        Project project = getAuthorizedProject(
                projectId, authenticatedEmail);

        Task task = taskRepository
                .findByIdAndProject_Id(taskId, project.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId));

        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateTask(
            Long projectId,
            Long taskId,
            TaskCreateRequest request,
            String authenticatedEmail) {

        Project project = getAuthorizedProject(
                projectId, authenticatedEmail);

        Task task = taskRepository
                .findByIdAndProject_Id(taskId, project.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId));

        task.setTitle(request.title());
        task.setDescription(request.description());

        if (request.status() != null) {
            task.setStatus(request.status());
        }

        if (request.priority() != null) {
            task.setPriority(request.priority());
        }

        Task updatedTask = taskRepository.save(task);

        return TaskResponse.from(updatedTask);
    }

    @Transactional
    public void deleteTask(
            Long projectId,
            Long taskId,
            String authenticatedEmail) {

        Project project = getAuthorizedProject(
                projectId, authenticatedEmail);

        Task task = taskRepository
                .findByIdAndProject_Id(taskId, project.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId));

        taskRepository.delete(task);
    }
}
