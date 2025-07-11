package com.fox.storey.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String role;
    private boolean isActive;
}
