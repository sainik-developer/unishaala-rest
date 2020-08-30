package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateClassLaunchResponseDTO {
    private String status;
    @NotEmpty
    @JsonProperty(value = "class_id")
    private String classId;
    @JsonProperty(value = "launch_url")
    private String launchurl;
    @JsonProperty(value = "encryptedlaunch_url")
    private String encryptedlaunchurl;
}
