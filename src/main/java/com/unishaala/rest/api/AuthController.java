package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.RequestLoginDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.LoginException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.UserRepository;
import com.unishaala.rest.service.AuthService;
import com.unishaala.rest.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/admin/sendotp")
    public BaseResponseDTO sendAdminOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.ADMIN);
        if (userDO != null) {
            authService.sendOtp(formattedPhoneNumber);
            return BaseResponseDTO.builder().success(true).build();
        }
        throw new NotFoundException("phone Number does not belong to admin!");
    }

    @PostMapping("/teacher/sendotp")
    public BaseResponseDTO sendTeacherOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.TEACHER);
        if (userDO != null) {
            authService.sendOtp(formattedPhoneNumber);
            return BaseResponseDTO.builder().success(true).build();
        }
        throw new NotFoundException("Phone number does not belong to teacher!");
    }

    @PostMapping("/student/sendotp")
    public BaseResponseDTO sendStudentOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.STUDENT);
        if (userDO != null) {
            authService.sendOtp(formattedPhoneNumber);
            return BaseResponseDTO.builder().success(true).build();
        }
        throw new NotFoundException("Phone number does not belong to student!");
    }

    @PostMapping("/admin/verify")
    public BaseResponseDTO verifyAdminOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.ADMIN);
            final Map<String, Object> data = new HashMap<>();
            data.put("Authorization", jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.ADMIN), UserType.ADMIN));
            data.put("details", UserMapper.INSTANCE.toDTO(userDO));
            return BaseResponseDTO.builder().success(true).data(data).build();
        }
        throw new LoginException();
    }

    @PostMapping("/teacher/verify")
    public BaseResponseDTO verifyTeacherOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.TEACHER);
            final Map<String, Object> data = new HashMap<>();
            data.put("Authorization", jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.TEACHER), UserType.TEACHER));
            data.put("details", UserMapper.INSTANCE.toTeacherDTO(userDO));
            return BaseResponseDTO.builder().success(true).data(data).build();
        }
        throw new LoginException();
    }

    @PostMapping("/student/verify")
    public BaseResponseDTO verifyStudentOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.STUDENT);
            final Map<String, Object> data = new HashMap<>();
            data.put("Authorization", jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.TEACHER), UserType.TEACHER));
            data.put("details", UserMapper.INSTANCE.toStudentDTO(userDO));
            return BaseResponseDTO.builder().success(true).data(data).build();
        }
        throw new LoginException();
    }
}



