package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unishaala.rest.validator.ValidUuid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotEmpty(message = "class name can't be empty. eg 10-A, 8-B where 10,8 are standards and A,B are sections")
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "school_id")
    @ValidUuid(message = "school id is required")
    private UUID schoolId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private SchoolDTO school;
}
