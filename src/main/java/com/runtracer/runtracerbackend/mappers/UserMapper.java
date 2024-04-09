package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.model.User;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);

    default UUID map(UUID value) {
        return value;
    }
}