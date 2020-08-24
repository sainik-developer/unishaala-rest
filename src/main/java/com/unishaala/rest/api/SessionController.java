package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public BaseResponseDTO createSession(final Principal principal, @Validated @RequestBody SessionDTO sessionDTO) {
        return BaseResponseDTO.builder()
                .success(true)
                .data(sessionService.createSession(UUID.fromString(principal.getName()), sessionDTO))
                .build();
    }

}
