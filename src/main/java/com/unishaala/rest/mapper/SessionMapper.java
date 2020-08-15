package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.model.SessionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionDTO toDTO(final SessionDO sessionDO);

    SessionDO fromDTO(final SessionDTO schoolDTO);
}
