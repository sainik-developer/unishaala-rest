package com.unishaala.rest.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuplicateException extends RuntimeException {
    private final String message;
}
