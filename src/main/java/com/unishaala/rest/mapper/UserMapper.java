package com.unishaala.rest.mapper;

import com.unishaala.rest.dto.StudentDTO;
import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.dto.AdminDTO;
import com.unishaala.rest.model.UserDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ClassMapper.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AdminDTO toDTO(final UserDO userDO);

    UserDO fromDTO(final AdminDTO adminDTO);

    @Mapping(target = "userName", expression = "java(teacherDTO.getFirstName() + \".\" + teacherDTO.getLastName())")
    @Mapping(target = "userType", defaultValue = "TEACHER")
    UserDO fromTeacherDTO(TeacherDTO teacherDTO);

    @Mapping(target = "firstName", expression = "java(userDO.getUserName().split(\".\")[0])")
    @Mapping(target = "lastName", expression = "java(userDO.getUserName().split(\".\")[1])")
    TeacherDTO toTeacherDTO(UserDO userDO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userName", expression = "java(fromTeacherDTO.getFirstName() + \".\" + fromTeacherDTO.getLastName())")
    void updateUserDO(@MappingTarget UserDO toUserDO, TeacherDTO fromTeacherDTO);

    StudentDTO toStudentDTO(UserDO userDO);

    UserDO fromStudentDTO(StudentDTO studentDTO);
}
