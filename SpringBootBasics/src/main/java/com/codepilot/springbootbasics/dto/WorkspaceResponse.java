package com.codepilot.springbootbasics.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Workspace details returned by the API")
public class WorkspaceResponse {

    @Schema(description = "Unique workspace ID", example = "1")
    private Long id;

    @Schema(description = "Workspace name", example = "My Development Workspace")
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