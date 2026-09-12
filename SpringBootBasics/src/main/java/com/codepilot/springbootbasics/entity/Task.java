package com.codepilot.springbootbasics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;



    protected Task() {
    }

    public Task(String title, String description, String status, Project project) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Project getProject() {
        return project;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}