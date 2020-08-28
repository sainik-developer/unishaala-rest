package com.unishaala.rest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicateException extends RuntimeException {
    private final String message;
}
