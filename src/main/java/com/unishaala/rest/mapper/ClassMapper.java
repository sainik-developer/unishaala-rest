package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.ClassDTO;
import com.unishaala.rest.model.ClassDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SchoolMapper.class)
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    ClassDTO toDTO(final ClassDO classDO);

    ClassDO fromDTO(final ClassDTO classDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ClassDO toClassDO, ClassDTO fromClassDTO);
}
