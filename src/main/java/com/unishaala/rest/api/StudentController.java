package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.dto.StudentDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.BraincertRepository;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.SessionRepository;
import com.unishaala.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;


    @PostMapping("/register")
    public BaseResponseDTO registerStudent(@Validated @RequestBody StudentDTO studentDTO) {
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(studentDTO.getMobileNumber(), UserType.STUDENT);
        final ClassDO classDo = classRepository.findById(studentDTO.getClassId()).orElse(null);
        if (userDO == null && classDo != null) {
            studentDTO.setUserType(UserType.STUDENT);
            studentDTO.setAClass(ClassMapper.INSTANCE.toDTO(classDo));
            final UserDO userDo = userRepository.save(
                    UserMapper.INSTANCE.fromStudentDTO(studentDTO));
            return BaseResponseDTO.builder()
                    .success(true)
                    .data(UserMapper.INSTANCE.toStudentDTO(userDo))
                    .build();
        }
        throw new DuplicateException("Student with given mobile number already exist!");
    }


}
