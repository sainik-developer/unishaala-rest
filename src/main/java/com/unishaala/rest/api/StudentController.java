package com.unishaala.rest.api;

import com.unishaala.rest.dto.CourseDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.dto.StudentDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.mapper.CourseMapper;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;
    private final BraincertRepository braincertRepository;

    @PostMapping("/register")
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

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<StudentDTO> searchTeacherDTO(@RequestParam(value = "student-name", required = false) final String studentName,
                                             @RequestParam(value = "page", defaultValue = "0") final int page,
                                             @RequestParam(value = "size", defaultValue = "20") final int size) {
        return userRepository.findByUserNameContainingAndUserType(studentName, UserType.STUDENT, PageRequest.of(page, size))
                .map(UserMapper.INSTANCE::toStudentDTO);
    }

    @GetMapping("/sessions")
    @PreAuthorize("hasAuthority('STUDENT')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<SessionDTO> getAllSession(final Principal principal,
                                          @RequestParam(value = "page", defaultValue = "0") final int page,
                                          @RequestParam(value = "size", defaultValue = "20") final int size) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null && userDO.getRelatedClass() != null && userDO.getUserType() == UserType.STUDENT) {
            final Page<SessionDO> sessionDos = sessionRepository.findByAClass(userDO.getRelatedClass(), PageRequest.of(page, size));
            return sessionDos
                    .map(sessionDo -> {
                        final BraincertDO braincertDO = braincertRepository.findByUserAndSession(userDO, sessionDo);
                        if (braincertDO == null) {
                            throw new NotFoundException("Something went wrong braincert url not found!");
                        }
                        final SessionDTO sessionDTO = SessionMapper.INSTANCE.toDTO(sessionDo);
                        sessionDTO.setBraincertUrl(braincertDO.getUrl());
                        return sessionDTO;
                    });
        }
        throw new NotFoundException("User is not not a student may be!");
    }

    @GetMapping("/courses")
    @PreAuthorize("hasAuthority('STUDENT')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<CourseDTO> getAllCourse(final Principal principal,
                                        @RequestParam(value = "page", defaultValue = "0") final int page,
                                        @RequestParam(value = "size", defaultValue = "20") final int size) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null && userDO.getRelatedClass() != null && userDO.getUserType() == UserType.STUDENT) {
            final Page<SessionDO> sessionDos = sessionRepository.findByAClass(userDO.getRelatedClass(), PageRequest.of(page, size));
            return sessionDos.map(SessionDO::getCourse)
                    .map(CourseMapper.INSTANCE::toDTO);
        }
        throw new NotFoundException("User is not not a student may be!");
    }
}
