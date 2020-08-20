package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.TeacherDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.UserRepository;
import com.unishaala.rest.service.AWSS3Service;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    private static UserMapper UM = UserMapper.INSTANCE;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO addTeacher(@RequestBody @Validated TeacherDTO teacherDTO) {
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(teacherDTO.getMobileNumber(), UserType.TEACHER);
        if (userDO == null) {
            return BaseResponseDTO.builder().success(true).data(UM.toTeacherDTO(userRepository.save(UM.fromTeacherDTO(teacherDTO)))).build();
        }
        throw new DuplicateException("Teacher already exist with mobile number!");
    }

    @PutMapping("/modify/{teacher_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO modifyTeacher(@PathVariable("teacher_id") final UUID teacherId, @RequestBody @Validated TeacherDTO teacherDTO) {
        final UserDO userDO = userRepository.findByIdAndMobileNumberAndUserType(teacherId, teacherDTO.getMobileNumber(), UserType.TEACHER);
        if (userDO != null) {
            UM.updateUserDO(userDO, teacherDTO);
            return BaseResponseDTO.builder().success(true).data(UM.toTeacherDTO(userRepository.save(userDO))).build();
        }
        throw new NotFoundException("Teacher not found to modify!");
    }

    @PostMapping("/upload/profile/{teacher_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO uploadTeacherProfile(@PathVariable("teacher_id") final UUID teacherId, @RequestParam("file") MultipartFile file) {
        final UserDO userDO = userRepository.findById(teacherId).orElse(null);
        if (userDO != null && userDO.getUserType() == UserType.TEACHER) {
            final String avatarUrl = awss3Service.uploadFileInS3(file);
            userDO.setAvatarUrl(avatarUrl);
            return BaseResponseDTO.builder().success(true).data(UM.toTeacherDTO(userRepository.save(userDO))).build();
        }
        throw new NotFoundException("Teacher not found to modify profile pic!");
    }


}
