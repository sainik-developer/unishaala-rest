package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.model.TeacherDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDTO toDTO(final TeacherDO teacherDO);

    TeacherDO fromDTO(final TeacherDTO teacherDTO);
}
