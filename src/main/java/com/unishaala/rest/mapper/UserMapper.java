package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.dto.UserDTO;
import com.unishaala.rest.model.UserDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(final UserDO userDO);

    UserDO fromDTO(final UserDTO userDTO);

    @Mapping(target = "userName", expression = "java(teacherDTO.getFirstName() + \".\" + teacherDTO.getLastName())")
    @Mapping(target = "userType", defaultValue = "TEACHER")
    UserDO fromTeacherDTO(TeacherDTO teacherDTO);

    @Mapping(target = "firstName", expression = "java(userName.split(\".\")[0])")
    @Mapping(target = "lastName", expression = "java(userName.split(\".\")[1])")
    TeacherDTO toTeacherDTO(UserDO userDO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userName", expression = "java(teacherDTO.getFirstName() + \".\" + teacherDTO.getLastName())")
    void updateUserDO(@MappingTarget UserDO toUserDO, TeacherDTO fromTeacherDTO);



}
