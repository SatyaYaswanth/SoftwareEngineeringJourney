package com.codepilot.springbootbasics.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    protected Project() {
    }

    public Project(String name, String description, Workspace workspace) {
        this.name = name;
        this.description = description;
        this.workspace = workspace;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}