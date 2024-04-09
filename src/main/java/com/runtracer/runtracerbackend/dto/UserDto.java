package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String password;
    private String email;
    private String googleId;
    private String imageUrl;
    private List<RoleDto> roles;
}