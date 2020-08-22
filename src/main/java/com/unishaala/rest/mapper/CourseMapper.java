package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.CourseDTO;
import com.unishaala.rest.model.CourseDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO toDTO(final CourseDO courseDO);

    CourseDO fromDTO(final CourseDTO courseDTO);
}
