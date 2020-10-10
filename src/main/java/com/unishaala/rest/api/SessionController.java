package com.unishaala.rest.api;

import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.BadAccessException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.model.*;
import com.unishaala.rest.repository.*;
import com.unishaala.rest.service.AWSS3Service;
import com.unishaala.rest.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/sessions")
public class SessionController {
    private final AWSS3Service awss3Service;
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;
    private final BraincertRepository braincertRepository;
    private final AttachmentRepository attachmentRepository;


    @PostMapping("/{teacher-id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public List<SessionDTO> createSession(final Principal principal,
                                          @PathVariable("teacher-id") final UUID teacherId,
                                          @Validated @RequestBody SessionDTO sessionDTO) {
        if (principal.getName().equals(teacherId.toString())) {
            return sessionService.createSession(teacherId, sessionDTO);
        }
        throw new BadAccessException("Illegal access!");
    }

    @PostMapping(value = "/{teacher-id}/attachment/{session-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public SessionDTO uploadSessionAttachment(final Principal principal,
                                              @PathVariable("teacher-id") final UUID teacherId,
                                              @PathVariable("session-id") final UUID sessionId,
                                              @Valid @NotBlank @RequestParam("name") final String attachmentName,
                                              @RequestPart("file") MultipartFile file) {
        final SessionDO sessionDO = sessionRepository.findById(sessionId).orElse(null);
        if (principal.getName().equals(teacherId.toString()) && sessionDO != null &&
                sessionDO.getCourse().getTeacher().getId().equals(UUID.fromString(principal.getName()))) {
            final String attachmentUrl = awss3Service.uploadFileInS3(file);
            final AttachmentDO attachmentDO = attachmentRepository.save(AttachmentDO.builder().name(attachmentName).url(attachmentUrl).build());
            if (sessionDO.getAttachments() == null) {
                sessionDO.setAttachments(new LinkedList<>());
            }
            sessionDO.getAttachments().add(attachmentDO);
            sessionRepository.save(sessionDO);
            return SessionMapper.INSTANCE.toDTO(sessionDO);
        }
        throw new NotFoundException("Only teacher who owns the Session can attach a doc!");
    }

    @DeleteMapping(value = "/{teacher-id}/attachment/{session-id}/{attachment-id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public SessionDTO removeSessionAttachment(final Principal principal,
                                              @PathVariable("teacher-id") final UUID teacherId,
                                              @PathVariable("session-id") final UUID sessionId,
                                              @PathVariable("attachment-id") final UUID attachmentId) {
        final SessionDO sessionDO = sessionRepository.findById(sessionId).orElse(null);
        final AttachmentDO attachmentDO = attachmentRepository.findById(attachmentId).orElse(null);
        if (principal.getName().equals(teacherId.toString()) && sessionDO != null &&
                sessionDO.getCourse().getTeacher().getId().equals(UUID.fromString(principal.getName())) && attachmentDO != null) {
            attachmentRepository.deleteById(attachmentId);
            sessionDO.getAttachments().remove(attachmentDO);
            sessionRepository.save(sessionDO);
            return SessionMapper.INSTANCE.toDTO(sessionDO);
        }
        throw new NotFoundException("Only teacher who owns the Session can remove a doc!");
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<SessionDTO> getAllSession(final Principal principal,
                                          @RequestParam("teacher-id") final UUID teacherId,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                          @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        final UserDO userDO = userRepository.findById(UUID.fromString(principal.getName())).orElse(null);
        final List<CourseDO> courseDOs = courseRepository.findByTeacher(userDO);
        if (principal.getName().equals(teacherId.toString()) && userDO != null
                && userDO.getUserType() == UserType.TEACHER
                && courseDOs != null && !courseDOs.isEmpty()) {
            return sessionRepository.findByCourseIn(courseDOs, PageRequest.of(page, size))
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
        throw new NotFoundException("User is not not a teacher or has no course may be!");
    }
}
