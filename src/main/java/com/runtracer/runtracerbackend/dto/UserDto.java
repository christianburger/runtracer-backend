package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String googleId;
    private String imageUrl;
    private List<RoleDto> roles;
}