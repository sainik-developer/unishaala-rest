package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.SchoolDTO;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.SchoolMapper;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.repository.SchoolRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolRepository schoolRepository;

    @GetMapping("/test")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO test(final Principal principal) {
        return BaseResponseDTO.builder().success(true).data(principal.getName()).build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO addSchools(@RequestBody @Validated SchoolDTO schoolDTO) {
        final SchoolDO schoolDO = schoolRepository.findByName(schoolDTO.getName());
        if (schoolDO == null) {
            return BaseResponseDTO.builder().data(SchoolMapper.INSTANCE.toDTO(schoolRepository.save(SchoolMapper.INSTANCE.fromDTO(schoolDTO)))).success(true).build();
        }
        throw new DuplicateException("School name has to be unique!");
    }

    @PutMapping("/modify/{schoolid}")
    @PreAuthorize("hasRole('ADMIN')")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO modifySchools(@PathVariable("schoolid") final UUID schoolId, @RequestBody @Validated SchoolDTO schoolDTO) {
        return schoolRepository.findById(schoolId)
                .map(schoolDO -> {
                    SchoolMapper.INSTANCE.update(schoolDO, schoolDTO);
                    return schoolDO;
                })
                .map(schoolDO -> schoolRepository.save(schoolDO))
                .map(SchoolMapper.INSTANCE::toDTO)
                .map(schoolDto -> BaseResponseDTO.builder().success(true).data(schoolDto).build())
                .orElseThrow(() -> new NotFoundException("school id is not found!"));
    }


}
