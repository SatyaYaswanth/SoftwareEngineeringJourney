package com.codepilot.springbootbasics.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workspaces")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CodePilotUser user;

    @OneToMany(mappedBy = "workspace")
    private List<Project> projects = new ArrayList<>();

    protected Workspace() {
    }

    public Workspace(String name, CodePilotUser user) {
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CodePilotUser getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(CodePilotUser user) {
        this.user = user;
    }

    public List<Project> getProjects() {
        return projects;
    }
}