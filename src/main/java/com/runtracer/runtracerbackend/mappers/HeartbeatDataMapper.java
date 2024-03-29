package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.HeartbeatDataDto;
import com.runtracer.runtracerbackend.model.activity.HeartbeatData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HeartbeatDataMapper {
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "heartbeat", target = "heartbeat")
    HeartbeatDataDto toDto(HeartbeatData heartbeatData);

    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "heartbeat", target = "heartbeat")
    HeartbeatData toEntity(HeartbeatDataDto heartbeatDataDto);
}