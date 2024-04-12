package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.RoleDto;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.dtos.ApiRoleDto;
import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.utils.ApiUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface ApiUserResponseMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "userId", target = "userId", ignore = true)
    UserDto toUserDto(ApiUserResponse.UserDto apiUserDto);

    default List<RoleDto> map(List<ApiRoleDto> roles) {
        return roles.stream().map(this::toRoleDto).collect(Collectors.toList());
    }

    @Mapping(source = "name", target = "name", qualifiedByName = "mapRoleType")
    @Mapping(source = "roleId", target = "roleId", ignore = true) // Ignoring UUID field
    RoleDto toRoleDto(ApiRoleDto roleDto);

    @Named("mapRoleType")
    default Role.RoleType mapRoleType(String value) {
        return switch (value.toLowerCase()) {
            case "user" -> Role.RoleType.ROLE_USER;
            case "admin" -> Role.RoleType.ROLE_ADMIN;
            default -> throw new IllegalArgumentException("Invalid role type: " + value);
        };
    }
}