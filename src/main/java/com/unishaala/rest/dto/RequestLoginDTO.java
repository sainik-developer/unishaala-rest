package com.unishaala.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestLoginDTO {
    @NotEmpty
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    private String otp;
}
