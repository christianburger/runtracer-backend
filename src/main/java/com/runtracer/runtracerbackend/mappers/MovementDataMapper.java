package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.MovementDataDto;
import com.runtracer.runtracerbackend.model.activity.MovementData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementDataMapper {
    MovementDataDto toDto(MovementData movementData);
    MovementData toEntity(MovementDataDto movementDataDto);
}