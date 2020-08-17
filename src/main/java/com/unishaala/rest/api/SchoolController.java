package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/schools")
@RequiredArgsConstructor
public class SchoolController {

    @GetMapping("/test")
    public BaseResponseDTO test() {
        return BaseResponseDTO.builder().success(true).build();
    }
}
