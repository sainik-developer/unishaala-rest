package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.model.AttachmentDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.repository.AttachmentRepository;
import com.unishaala.rest.repository.SessionRepository;
import com.unishaala.rest.service.AWSS3Service;
import com.unishaala.rest.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.LinkedList;
import java.util.UUID;

@Log4j2
@RestController
@Validated
@RequestMapping("/rest/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final AWSS3Service awss3Service;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final AttachmentRepository attachmentRepository;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public BaseResponseDTO createSession(final Principal principal, @Validated @RequestBody SessionDTO sessionDTO) {
        return BaseResponseDTO.builder()
                .success(true)
                .data(sessionService.createSession(UUID.fromString(principal.getName()), sessionDTO))
                .build();
    }


    @PostMapping(value = "/add/attachment/{session_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public BaseResponseDTO uploadOwnProfile(final Principal principal,
                                            @PathVariable("session_id") final UUID sessionId,
                                            @Valid @NotBlank @RequestParam("name") final String attachmentName,
                                            @RequestPart("file") MultipartFile file) {
        final SessionDO sessionDO = sessionRepository.findById(sessionId).orElse(null);
        if (sessionDO != null && sessionDO.getCourse().getTeacher().getId().equals(UUID.fromString(principal.getName()))) {
            final String attachmentUrl = awss3Service.uploadFileInS3(file);
            final AttachmentDO attachmentDO = attachmentRepository.save(AttachmentDO.builder().name(attachmentName).url(attachmentUrl).build());
            if (sessionDO.getAttachments() == null) {
                sessionDO.setAttachments(new LinkedList<>());
            }
            sessionDO.getAttachments().add(attachmentDO);
            sessionRepository.save(sessionDO);
            return BaseResponseDTO.builder().data(SessionMapper.INSTANCE.toDTO(sessionDO)).success(true).build();
        }
        throw new NotFoundException("Only teacher who owns the Session can attach a doc!");
    }

    @DeleteMapping(value = "/remove/attachment/{session_id}/{attachment_id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public BaseResponseDTO uploadOwnProfile(final Principal principal,
                                            @PathVariable("session_id") final UUID sessionId,
                                            @PathVariable("attachment_id") final UUID attachmentId) {
        final SessionDO sessionDO = sessionRepository.findById(sessionId).orElse(null);
        final AttachmentDO attachmentDO = attachmentRepository.findById(attachmentId).orElse(null);
        if (sessionDO != null && sessionDO.getCourse().getTeacher().getId().equals(UUID.fromString(principal.getName())) && attachmentDO != null) {
            attachmentRepository.deleteById(attachmentId);
            sessionDO.getAttachments().remove(attachmentDO);
            sessionRepository.save(sessionDO);
            return BaseResponseDTO.builder().data(SessionMapper.INSTANCE.toDTO(sessionDO)).success(true).build();
        }
        throw new NotFoundException("Only teacher who owns the Session can remove a doc!");
    }
}
