package com.unishaala.rest.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MobileNumberFormatException extends RuntimeException {
    private final String message;
}
