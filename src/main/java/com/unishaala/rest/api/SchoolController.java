package com.unishaala.rest.api;

import com.unishaala.rest.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rest/schools")
@RequiredArgsConstructor
public class SchoolController {

    @GetMapping("/test")
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer <jwt-token>",
                    required = true, schema = @Schema(type = "string"), in = ParameterIn.HEADER)})
    public BaseResponseDTO test(final Principal principal) {
        return BaseResponseDTO.builder().success(true).data(principal.getName()).build();
    }
}
