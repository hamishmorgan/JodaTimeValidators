package com.github.hamishmorgan.jodatimevalidators;

import org.joda.time.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("deprecation")
public class BeforeValidatorForReadableInstant implements ConstraintValidator<Before, ReadableInstant> {

    private ReadablePartial partial;

    @Override
    public void initialize(@Nonnull Before constraintAnnotation) {
        checkNotNull(constraintAnnotation, "constraintAnnotation");

        Partial partial = new Partial();

        if(constraintAnnotation.year() != Before.NO_YEAR) {
            partial = partial.with(DateTimeFieldType.yearOfEra(), constraintAnnotation.year());
        }

        this.partial = partial;
    }

    @Override
    public boolean isValid(@Nullable ReadableInstant value, @Nonnull ConstraintValidatorContext context) {
        checkNotNull(context, "context");

        if(value == null) {
            return true;
        }

        DateTime instant = partial.toDateTime(value);
        return value == null || value.isBefore(instant);
    }
}