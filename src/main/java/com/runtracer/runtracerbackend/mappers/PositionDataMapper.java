package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.PositionDataDto;
import com.runtracer.runtracerbackend.model.activity.PositionData;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PositionDataMapper {
    PositionDataDto toDto(PositionData positionData);
    PositionData toEntity(PositionDataDto positionDataDto);

    default UUID map(UUID value) {
        return value;
    }
}