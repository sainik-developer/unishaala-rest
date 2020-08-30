package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Log4j2
@Validated
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SchoolRepository schoolRepository;
    private final ClassRepository classRepository;

    @GetMapping("/school")
    public BaseResponseDTO searchSchool(@RequestParam(value = "key", defaultValue = "") final String key,
                                        @RequestParam(value = "page", defaultValue = "0") final int page,
                                        @RequestParam(value = "size", defaultValue = "20") final int size) {
        return BaseResponseDTO.builder()
                .data(schoolRepository.findByNameContaining(key, PageRequest.of(page, size)))
                .success(true)
                .build();
    }

    @GetMapping("/class")
    public BaseResponseDTO searchClass(@NotNull @RequestParam("school-id") final UUID schoolId,
                                       @RequestParam(value = "page", defaultValue = "0") final int page,
                                       @RequestParam(value = "size", defaultValue = "20") final int size) {
        final SchoolDO schoolDO = schoolRepository.findById(schoolId).orElse(null);
        if (schoolDO != null) {
            return BaseResponseDTO.builder()
                    .data(classRepository.findBySchoolContaining(schoolDO, PageRequest.of(page, size)))
                    .success(true)
                    .build();
        }
        throw new NotFoundException("Invalid school Id");
    }
}
