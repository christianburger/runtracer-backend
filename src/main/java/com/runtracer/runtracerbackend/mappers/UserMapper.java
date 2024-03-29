package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles")
    UserDto toDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles")
    User toEntity(UserDto userDto);
}