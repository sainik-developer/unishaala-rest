package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.UserDTO;
import com.unishaala.rest.model.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(final UserDO userDO);

    UserDO fromDTO(final UserDTO userDTO);
}
