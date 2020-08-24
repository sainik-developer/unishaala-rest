package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.SchoolDTO;
import com.unishaala.rest.model.SchoolDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchoolMapper {
    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    SchoolDTO toDTO(final SchoolDO schoolDO);

    SchoolDO fromDTO(final SchoolDTO schoolDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget SchoolDO toSchoolDO, SchoolDTO fromSchoolDTO);
}
