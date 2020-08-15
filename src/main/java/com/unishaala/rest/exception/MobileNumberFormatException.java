package com.unishaala.rest.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MobileNumberFormatException extends RuntimeException{
    private final String message;
}
