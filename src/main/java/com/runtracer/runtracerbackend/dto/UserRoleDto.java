package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleDto {
    private UUID userRoleId;
    private UUID userId;
    private UUID roleId;
}