package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.StepDataDto;
import com.runtracer.runtracerbackend.model.activity.StepData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepDataMapper {
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "steps", target = "steps")
    StepDataDto toDto(StepData stepData);

    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "steps", target = "steps")
    StepData toEntity(StepDataDto stepDataDto);
}