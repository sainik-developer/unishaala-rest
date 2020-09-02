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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;

    @PostMapping("/add")
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

    @PutMapping("/modify/{class_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public ClassDTO modifySchools(@PathVariable("class_id") final UUID classId, @RequestBody @Validated ClassDTO classDTO) {
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
}
