package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(value = "name")
    private String className;
}
