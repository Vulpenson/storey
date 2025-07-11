package com.fox.storey.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private String error;
    private String timestamp;
}
