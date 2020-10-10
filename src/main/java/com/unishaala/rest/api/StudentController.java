package com.unishaala.rest.api;

import com.unishaala.rest.dto.CourseDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.dto.StudentDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.CourseMapper;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.BraincertRepository;
import com.unishaala.rest.repository.SessionRepository;
import com.unishaala.rest.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/students")
public class StudentController {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BraincertRepository braincertRepository;


    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<StudentDTO> searchStudents(@RequestParam(value = "student-name", required = false) final String studentName,
                                           @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                           @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        return userRepository.findByUserNameContainingAndUserType(studentName, UserType.STUDENT, PageRequest.of(page, size))
                .map(UserMapper.INSTANCE::toStudentDTO);
    }

    @GetMapping("/{student-id}/session")
    @PreAuthorize("hasAuthority('STUDENT')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<SessionDTO> getAllSession(final Principal principal,
                                          @PathParam("student-id") final UUID studentId,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                          @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (principal.getName().equals(studentId.toString()) && userDO != null && userDO.getRelatedClass() != null) {
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

    @GetMapping("/{student-id}/courses")
    @PreAuthorize("hasAuthority('STUDENT')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<CourseDTO> getAllCourse(final Principal principal,
                                        @PathParam("student-id") final UUID studentId,
                                        @RequestParam(value = "page", defaultValue = "0") final int page,
                                        @RequestParam(value = "size", defaultValue = "20") final int size) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (principal.getName().equals(studentId.toString()) && userDO != null && userDO.getRelatedClass() != null && userDO.getUserType() == UserType.STUDENT) {
            final Page<SessionDO> sessionDos = sessionRepository.findByAClass(userDO.getRelatedClass(), PageRequest.of(page, size));
            return sessionDos.map(SessionDO::getCourse)
                    .map(CourseMapper.INSTANCE::toDTO);
        }
        throw new NotFoundException("User is not not a student may be!");
    }
}
