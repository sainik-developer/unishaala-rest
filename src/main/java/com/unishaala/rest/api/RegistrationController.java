package com.unishaala.rest.api;

import com.unishaala.rest.dto.StudentDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @PostMapping("/")
    public StudentDTO registerStudent(@Validated @RequestBody StudentDTO studentDTO) {
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(studentDTO.getMobileNumber(), UserType.STUDENT);
        final ClassDO classDo = classRepository.findById(studentDTO.getClassId()).orElse(null);
        if (userDO == null && classDo != null) {
            studentDTO.setUserType(UserType.STUDENT);
            studentDTO.setAClass(ClassMapper.INSTANCE.toDTO(classDo));
            final UserDO userDo = userRepository.save(
                    UserMapper.INSTANCE.fromStudentDTO(studentDTO));
            return UserMapper.INSTANCE.toStudentDTO(userDo);
        }
        throw new DuplicateException("Student with given mobile number already exist!");
    }
}
