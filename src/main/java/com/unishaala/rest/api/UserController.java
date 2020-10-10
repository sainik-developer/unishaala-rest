package com.unishaala.rest.api;

import com.unishaala.rest.dto.AdminDTO;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.UserRepository;
import com.unishaala.rest.service.AWSS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.UUID;

@Log4j2
@Validated
@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AWSS3Service awss3Service;

    @PostMapping(value = "/{user-id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public AdminDTO uploadOwnProfile(final Principal principal,
                                     @NotEmpty @PathParam("user-id") final UUID userId,
                                     @RequestPart("file") final MultipartFile file) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null && principal.getName().equals(userId.toString())) {
            final String avatarUrl = awss3Service.uploadFileInS3(file);
            userDO.setAvatarUrl(avatarUrl);
            return UserMapper.INSTANCE.toDTO(userRepository.save(userDO));
        }
        throw new NotFoundException("User not found.Please report the incident!");
    }
}
