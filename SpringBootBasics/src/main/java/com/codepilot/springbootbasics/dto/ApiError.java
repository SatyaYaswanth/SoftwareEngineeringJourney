package com.codepilot.springbootbasics.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API error response")
public class ApiError {

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error message", example = "Workspace not found")
    private String message;

    @Schema(description = "Time when the error occurred")
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiError(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }
}
