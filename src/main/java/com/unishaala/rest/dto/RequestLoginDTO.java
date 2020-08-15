package com.unishaala.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestLoginDTO {
    @NotEmpty
    private String phoneNumber;
    private String otp;
}
