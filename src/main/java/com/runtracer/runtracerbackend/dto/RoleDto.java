package com.runtracer.runtracerbackend.dto;

import com.runtracer.runtracerbackend.model.Role.RoleType;
import lombok.Data;

@Data
public class RoleDto {
    private Long roleId;
    private RoleType name;
}