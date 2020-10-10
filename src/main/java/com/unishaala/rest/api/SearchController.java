package com.unishaala.rest.api;

import com.unishaala.rest.dto.ClassDTO;
import com.unishaala.rest.dto.SchoolDTO;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.mapper.SchoolMapper;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
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
    public Page<SchoolDTO> searchSchool(@RequestParam(value = "name", defaultValue = StringUtils.EMPTY, required = false) final String name,
                                        @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                        @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        return schoolRepository.findByNameContaining(name, PageRequest.of(page, size))
                .map(SchoolMapper.INSTANCE::toDTO);
    }

    @GetMapping("/class")
    public Page<ClassDTO> searchClass(@NotEmpty @RequestParam("school-id") final UUID schoolId,
                                      @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                      @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        final SchoolDO schoolDO = schoolRepository.findById(schoolId).orElse(null);
        if (schoolDO != null) {
            return classRepository.findBySchool(schoolDO, PageRequest.of(page, size))
                    .map(ClassMapper.INSTANCE::toDTO);
        }
        throw new NotFoundException("Invalid school Id");
    }
}
