package com.unishaala.rest.api;

import com.unishaala.rest.dto.CourseDTO;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.exception.NotFoundException;
import com.unishaala.rest.mapper.CourseMapper;
import com.unishaala.rest.model.CourseDO;
import com.unishaala.rest.repository.CourseRepository;
import com.unishaala.rest.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/rest/courses")
@RequiredArgsConstructor
public class CourseController {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public CourseDTO addCourse(@RequestBody @Validated CourseDTO courseDTO) {
        return userRepository.findById(courseDTO.getTeacherId())
                .map(userDo -> {
                    final CourseDO courseDO = CourseMapper.INSTANCE.fromDTO(courseDTO);
                    courseDO.setTeacher(userDo);
                    return CourseMapper.INSTANCE.toDTO(courseRepository.save(courseDO));
                })
                .orElseThrow(() -> new NotFoundException("Teacher id not found!"));
    }

    @PutMapping("/{courser-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public CourseDTO modifyCourse(@PathVariable("courser-id") final UUID courserId,
                                  @RequestBody @Validated CourseDTO courseDTO) {
        return courseRepository.findById(courserId)
                .flatMap(courseDo ->
                        userRepository.findById(courseDTO.getTeacherId())
                                .filter(userDo -> userDo.getUserType() == UserType.TEACHER)
                                .map(userDo -> {
                                    final CourseDO courseDO = CourseMapper.INSTANCE.fromDTO(courseDTO);
                                    courseDO.setTeacher(userDo);
                                    return CourseMapper.INSTANCE.toDTO(courseRepository.save(courseDO));
                                }))
                .orElseThrow(() -> new NotFoundException("Course/Teacher id not found!"));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer")})
    public Page<CourseDTO> getAllCourses(final Principal principal,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                         @RequestParam(value = "size", defaultValue = "20", required = false) final int size) {
        return courseRepository.findAll(PageRequest.of(page, size))
                .map(CourseMapper.INSTANCE::toDTO);
    }

}
