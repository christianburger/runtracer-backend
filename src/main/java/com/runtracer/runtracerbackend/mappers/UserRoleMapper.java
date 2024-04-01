package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.UserRoleDto;
import com.runtracer.runtracerbackend.model.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDto toDto(UserRole userRole);
    UserRole toEntity(UserRoleDto userRoleDto);
}