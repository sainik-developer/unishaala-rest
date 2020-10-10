package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.SchoolDTO;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.SchoolMapper;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.repository.SchoolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/rest/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolRepository schoolRepository;

    @GetMapping("/test1")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public BaseResponseDTO test(final Principal principal) {
        return BaseResponseDTO.builder().success(true).data(principal.getName()).build();
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public SchoolDTO addSchools(@RequestBody @Validated SchoolDTO schoolDTO) {
        final SchoolDO schoolDO = schoolRepository.findByName(schoolDTO.getName());
        if (schoolDO == null) {
            schoolDTO.setCreatedDate(LocalDateTime.now());
            return SchoolMapper.INSTANCE.toDTO(schoolRepository.save(SchoolMapper.INSTANCE.fromDTO(schoolDTO)));
        }
        throw new DuplicateException("School name has to be unique!");
    }

    @PutMapping("/{school-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public SchoolDTO modifySchools(@PathVariable("school-id") final UUID schoolId,
                                   @RequestBody @Validated SchoolDTO schoolDTO) {
        return schoolRepository.findById(schoolId)
                .map(schoolDO -> {
                    SchoolMapper.INSTANCE.update(schoolDO, schoolDTO);
                    return schoolDO;
                })
                .map(schoolDO -> schoolRepository.save(schoolDO))
                .map(SchoolMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new NotFoundException("school id is not found!"));
    }
}
