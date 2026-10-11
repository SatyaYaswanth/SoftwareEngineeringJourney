package com.codepilot.springbootbasics.dto;

public class WorkspaceResponse {

    private Long id;
    private String name;

    public WorkspaceResponse() {
    }

    public WorkspaceResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}