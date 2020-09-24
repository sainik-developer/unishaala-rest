package com.unishaala.rest.api;

import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.UserRepository;
import com.unishaala.rest.service.AWSS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final UserRepository userRepository;
    private final AWSS3Service awss3Service;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public TeacherDTO addTeacher(@RequestBody @Validated TeacherDTO teacherDTO) {
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(teacherDTO.getMobileNumber(), UserType.TEACHER);
        if (userDO == null) {
            return UserMapper.INSTANCE.toTeacherDTO(userRepository.save(UserMapper.INSTANCE.fromTeacherDTO(teacherDTO)));
        }
        throw new DuplicateException("Teacher already exist with mobile number!");
    }

    @PutMapping("/modify/{teacher_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public TeacherDTO modifyTeacher(@PathVariable("teacher_id") final UUID teacherId, @RequestBody @Validated TeacherDTO teacherDTO) {
        final UserDO userDO = userRepository.findByIdAndMobileNumberAndUserType(teacherId, teacherDTO.getMobileNumber(), UserType.TEACHER);
        if (userDO != null) {
            UserMapper.INSTANCE.updateUserDO(userDO, teacherDTO);
            return UserMapper.INSTANCE.toTeacherDTO(userRepository.save(userDO));
        }
        throw new NotFoundException("Teacher not found to modify!");
    }

    @PostMapping("/upload/profile/{teacher_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public TeacherDTO uploadTeacherProfile(@PathVariable("teacher_id") final UUID teacherId, @RequestParam("file") MultipartFile file) {
        final UserDO userDO = userRepository.findById(teacherId).orElse(null);
        if (userDO != null && userDO.getUserType() == UserType.TEACHER) {
            final String avatarUrl = awss3Service.uploadFileInS3(file);
            userDO.setAvatarUrl(avatarUrl);
            return UserMapper.INSTANCE.toTeacherDTO(userRepository.save(userDO));
        }
        throw new NotFoundException("Teacher not found to modify profile pic!");
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<TeacherDTO> searchTeacherDTO(@RequestParam("teacher-name") final String teacherName,
                                             @RequestParam(value = "page", defaultValue = "0") final int page,
                                             @RequestParam(value = "size", defaultValue = "20") final int size){
        return userRepository.findByUserNameContainingAndUserType(teacherName,UserType.TEACHER, PageRequest.of(page, size))
                .map(UserMapper.INSTANCE::toTeacherDTO);
    }
}
