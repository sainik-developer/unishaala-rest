package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.model.AttachmentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachmentMapper {
    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    TeacherDTO toDTO(final AttachmentDO attachmentDO);

    AttachmentDO fromDTO(final TeacherDTO teacherDTO);
}
