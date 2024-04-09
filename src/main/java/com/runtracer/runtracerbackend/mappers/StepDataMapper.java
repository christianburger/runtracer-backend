package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.StepDataDto;
import com.runtracer.runtracerbackend.model.activity.StepData;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StepDataMapper {
    StepDataDto toDto(StepData stepData);
    StepData toEntity(StepDataDto stepDataDto);

    default UUID map(UUID value) {
        return value;
    }
}