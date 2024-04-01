package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class UserRoleDto {
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}