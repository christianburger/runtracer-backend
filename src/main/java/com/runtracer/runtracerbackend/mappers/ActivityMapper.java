package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.model.activity.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mapping(source = "positionData", target = "positionData")
    @Mapping(source = "heartbeatData", target = "heartbeatData")
    @Mapping(source = "movementData", target = "movementData")
    @Mapping(source = "stepData", target = "stepData")
    ActivityDto toDto(Activity activity);

    @Mapping(source = "positionData", target = "positionData")
    @Mapping(source = "heartbeatData", target = "heartbeatData")
    @Mapping(source = "movementData", target = "movementData")
    @Mapping(source = "stepData", target = "stepData")
    Activity toEntity(ActivityDto activityDto);
}
