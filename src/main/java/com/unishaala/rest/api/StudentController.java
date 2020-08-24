package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import com.unishaala.rest.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    @PostMapping("/add")
    public BaseResponseDTO addStudent(@Validated @RequestBody StudentDTO sessionDTO) {
        return BaseResponseDTO.builder()
                .success(true)
                .build();
    }
}
