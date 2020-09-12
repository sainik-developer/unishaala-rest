package com.unishaala.rest.api;

import com.unishaala.rest.dto.AdminDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.BraincertRepository;
import com.unishaala.rest.repository.SessionRepository;
import com.unishaala.rest.repository.UserRepository;
import com.unishaala.rest.service.AWSS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AWSS3Service awss3Service;
    private final SessionRepository sessionRepository;
    private BraincertRepository braincertRepository;

    @PostMapping(value = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public AdminDTO uploadOwnProfile(final Principal principal, @RequestPart("file") MultipartFile file) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null) {
            final String avatarUrl = awss3Service.uploadFileInS3(file);
            userDO.setAvatarUrl(avatarUrl);
            return UserMapper.INSTANCE.toDTO(userRepository.save(userDO));
        }
        throw new NotFoundException("User not found.Please report the incident!");
    }

    @GetMapping("/sessions")
    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<SessionDTO> getAllSession(final Principal principal, @NotNull final Pageable pageable) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        if (userDO != null && userDO.getRelatedClass() != null && userDO.getUserType() == UserType.STUDENT || userDO.getUserType() == UserType.TEACHER) {
            final Page<SessionDO> sessionDos = sessionRepository.findByAClass(userDO.getRelatedClass(), pageable);
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
        throw new NotFoundException("User is not not a student or teacher may be!");
    }
}
