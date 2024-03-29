package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.PositionDataDto;
import com.runtracer.runtracerbackend.model.activity.PositionData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionDataMapper {
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "height", target = "height")
    PositionDataDto toDto(PositionData positionData);

    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "height", target = "height")
    PositionData toEntity(PositionDataDto positionDataDto);
}