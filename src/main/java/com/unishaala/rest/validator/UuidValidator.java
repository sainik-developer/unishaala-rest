package com.unishaala.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UuidValidator implements ConstraintValidator<ValidUuid, UUID> {

    @Override
    public void initialize(ValidUuid validUuid) {
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {
        return !uuid.toString().isEmpty();
    }
}
