
package com.codepilot.springbootbasics.dto;

import com.codepilot.springbootbasics.entity.Project;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Project details returned by the API")
public record ProjectResponse(

        @Schema(description = "Unique project ID", example = "10")
        Long id,

        @Schema(description = "Project name", example = "CodePilot")
        String name,

        @Schema(description = "Project description", example = "Developer productivity platform")
        String description,

        @Schema(description = "ID of the parent workspace", example = "1")
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
