package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.MovementDataDto;
import com.runtracer.runtracerbackend.model.activity.MovementData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementDataMapper {
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "up", target = "up")
    @Mapping(source = "down", target = "down")
    @Mapping(source = "left", target = "left")
    @Mapping(source = "right", target = "right")
    MovementDataDto toDto(MovementData movementData);

    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "up", target = "up")
    @Mapping(source = "down", target = "down")
    @Mapping(source = "left", target = "left")
    @Mapping(source = "right", target = "right")
    MovementData toEntity(MovementDataDto movementDataDto);
}