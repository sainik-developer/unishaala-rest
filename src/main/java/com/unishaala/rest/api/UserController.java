package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.enums.UserType;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AWSS3Service awss3Service;
    private static UserMapper UM = UserMapper.INSTANCE;

    @PostMapping("/upload/profile")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO uploadOwnProfile(final Principal principal, @RequestParam("file") MultipartFile file) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null) {
            final String avatarUrl = awss3Service.uploadFileInS3(file);
            userDO.setAvatarUrl(avatarUrl);
            return BaseResponseDTO.builder().success(true).data(UM.toDTO(userRepository.save(userDO))).build();
        }
        throw new NotFoundException("User not found.Please report the incident!");
    }
}
