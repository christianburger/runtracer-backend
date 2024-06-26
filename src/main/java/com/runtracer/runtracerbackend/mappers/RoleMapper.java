package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.RoleDto;
import com.runtracer.runtracerbackend.model.Role;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

    default UUID map(UUID value) {
        return value;
    }
}