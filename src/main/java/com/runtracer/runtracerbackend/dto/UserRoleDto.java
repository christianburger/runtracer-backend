package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class UserRoleDto {
    private Long id;
    private Long userId;
    private Long roleId;
}