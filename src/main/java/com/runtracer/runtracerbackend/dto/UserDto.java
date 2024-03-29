package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<RoleDto> roles;
}