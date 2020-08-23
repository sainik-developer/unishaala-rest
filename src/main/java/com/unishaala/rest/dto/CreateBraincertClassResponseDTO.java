package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateBraincertClassResponseDTO {
    private String status;
    @NotEmpty
    @JsonProperty(value = "class_id")
    private String classId;
    private String title;
}
