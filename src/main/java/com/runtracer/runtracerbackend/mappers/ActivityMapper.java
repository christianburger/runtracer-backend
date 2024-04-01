package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.model.activity.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityDto toDto(Activity activity);
    Activity toEntity(ActivityDto activityDto);
}