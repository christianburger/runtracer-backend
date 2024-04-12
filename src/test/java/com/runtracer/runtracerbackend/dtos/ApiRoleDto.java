package com.runtracer.runtracerbackend.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ApiRoleDto {
    private String roleId;
    private String name;
}