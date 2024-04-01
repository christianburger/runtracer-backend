package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}