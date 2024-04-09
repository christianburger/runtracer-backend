package com.runtracer.runtracerbackend.dto;

import com.runtracer.runtracerbackend.model.Role.RoleType;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    private UUID roleId;
    private RoleType name;
}