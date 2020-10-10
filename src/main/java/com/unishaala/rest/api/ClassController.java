package com.unishaala.rest.api;

import com.unishaala.rest.dto.ClassDTO;
import com.unishaala.rest.exception.DuplicateException;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.SchoolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Log4j2
@Validated
@RestController
@RequestMapping("/rest/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public ClassDTO addClass(@RequestBody @Validated ClassDTO classDTO) {
        final SchoolDO schoolDO = schoolRepository.findById(classDTO.getSchoolId()).orElse(null);
        if (schoolDO != null) {
            final ClassDO classDO = classRepository.findByNameAndSchool(classDTO.getName(), schoolDO);
            if (classDO == null) {
                final ClassDO classDo = ClassMapper.INSTANCE.fromDTO(classDTO);
                classDo.setSchool(schoolDO);
                return ClassMapper.INSTANCE.toDTO(classRepository.save(classDo));
            }
            throw new DuplicateException("Duplicate course found!");
        }
        throw new NotFoundException("Associated school of class is not found!");
    }

    @PutMapping("/{class-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public ClassDTO modifySchools(@PathVariable("class-id") final UUID classId,
                                  @RequestBody @Validated ClassDTO classDTO) {
        final SchoolDO schoolDO = schoolRepository.findById(classDTO.getSchoolId()).orElse(null);
        if (schoolDO != null) {
            final ClassDO classDO = classRepository.findById(classId).orElse(null);
            if (classDO != null) {
                ClassMapper.INSTANCE.update(classDO, classDTO);
                return ClassMapper.INSTANCE.toDTO(classRepository.save(classDO));
            }
            throw new NotFoundException("Class with given ID is not found!");
        }
        throw new NotFoundException("Associated school of class is not found!");
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<ClassDTO> getClassBySchool(@NotEmpty @RequestParam("school-id") final UUID schoolId,
                                           @RequestParam(value = "page", defaultValue = "0") final int page,
                                           @RequestParam(value = "size", defaultValue = "20") final int size) {
        final SchoolDO schoolDO = schoolRepository.findById(schoolId).orElse(null);
        if (schoolDO != null) {
            return classRepository.findBySchool(schoolDO, PageRequest.of(page, size)).
                    map(ClassMapper.INSTANCE::toDTO);
        }
        throw new NotFoundException("School is found with given id!");
    }
}
