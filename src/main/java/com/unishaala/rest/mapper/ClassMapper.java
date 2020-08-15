package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.ClassDTO;
import com.unishaala.rest.model.ClassDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    ClassDTO toDTO(final ClassDO classDO);

    ClassDO fromDTO(final ClassDTO classDTO);
}
