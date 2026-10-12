
package com.codepilot.springbootbasics.dto;

import com.codepilot.springbootbasics.entity.Project;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        Long workspaceId
) {

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getWorkspace().getId()
        );
    }
}
