package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.SchoolDTO;
import com.unishaala.rest.model.SchoolDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchoolMapper {
    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);
    SchoolDTO toDTO(final SchoolDO schoolDO);
    SchoolDO fromDTO(final SchoolDTO schoolDTO);
}
