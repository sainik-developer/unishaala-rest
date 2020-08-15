package com.unishaala.rest.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final String message;
}
