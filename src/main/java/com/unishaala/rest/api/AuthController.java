package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.RequestLoginDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.LoginException;
import com.unishaala.rest.exception.UserNotFoundException;
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
        throw new UserNotFoundException("phone Number does not belong to admin!");
    }

    @PostMapping("/teacher/sendotp")
    public BaseResponseDTO sendTeacherOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.TEACHER);
        if (userDO != null) {
            authService.sendOtp(formattedPhoneNumber);
            return BaseResponseDTO.builder().success(true).build();
        }
        throw new UserNotFoundException("Phone number does not belong to teacher!");
    }

    @PostMapping("/student/sendotp")
    public BaseResponseDTO sendStudentOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.STUDENT);
        if (userDO != null) {
            authService.sendOtp(formattedPhoneNumber);
            return BaseResponseDTO.builder().success(true).build();
        }
        throw new UserNotFoundException("Phone number does not belong to student!");
    }

    @PostMapping("/admin/verify")
    public BaseResponseDTO verifyAdminOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.ADMIN);
            return BaseResponseDTO.builder().success(true).data(jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.ADMIN), UserType.ADMIN)).build();
        }
        throw new LoginException();
    }

    @PostMapping("/teacher/verify")
    public BaseResponseDTO verifyTeacherOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.TEACHER);
            return BaseResponseDTO.builder().success(true).data(jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.TEACHER), UserType.TEACHER)).build();
        }
        throw new LoginException();
    }

    @PostMapping("/student/verify")
    public BaseResponseDTO verifyStudentOtp(@RequestBody @Validated RequestLoginDTO requestLoginDTO) {
        final String formattedPhoneNumber = authService.getFormattedNumber(requestLoginDTO.getPhoneNumber());
        if (authService.verifyOtp(requestLoginDTO.getPhoneNumber(), requestLoginDTO.getOtp())) {
            final UserDO userDO = userRepository.findByMobileNumberAndUserType(formattedPhoneNumber, UserType.STUDENT);
            return BaseResponseDTO.builder().success(true).data(jwtService.createToken(jwtService.userClaims(userDO.getId(), UserType.STUDENT), UserType.STUDENT)).build();
        }
        throw new LoginException();
    }
}



