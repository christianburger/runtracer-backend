package com.runtracer.runtracerbackend.dto;

import com.runtracer.runtracerbackend.model.Role.RoleType;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleType name;
}