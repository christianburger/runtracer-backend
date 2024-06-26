package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.HeartbeatDataDto;
import com.runtracer.runtracerbackend.model.activity.HeartbeatData;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface HeartbeatDataMapper {
    HeartbeatDataDto toDto(HeartbeatData heartbeatData);
    HeartbeatData toEntity(HeartbeatDataDto heartbeatDataDto);

    default UUID map(UUID value) {
        return value;
    }
}