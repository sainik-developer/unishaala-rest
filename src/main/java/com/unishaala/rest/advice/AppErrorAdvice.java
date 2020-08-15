package com.unishaala.rest.advice;

import com.unishaala.rest.dto.BaseErrorDTO;
import com.unishaala.rest.exception.LoginException;
import com.unishaala.rest.exception.UserNotFoundException;
import com.unishaala.rest.exception.MobileNumberFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppErrorAdvice {
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public BaseErrorDTO notAdminException(final Exception e) {
        return new BaseErrorDTO(e.getMessage());
    }

    @ExceptionHandler(value = MobileNumberFormatException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public BaseErrorDTO mobileNumberFormatError(final Exception e) {
        return new BaseErrorDTO(e.getMessage());
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public BaseErrorDTO loginException(final Exception e) {
        return new BaseErrorDTO("Otp verification failed!");
    }
}
