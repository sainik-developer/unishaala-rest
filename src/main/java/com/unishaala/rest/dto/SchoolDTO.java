package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class SchoolDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank(message = "schools name can't be null")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss Z", timezone = "Asia/Kolkata")
    private LocalDateTime createdDate;
    @NotBlank(message = "schools name can't be null")
    private String address;
}
