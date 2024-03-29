package com.github.hamishmorgan.validators.jodatime;

import org.joda.time.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.github.hamishmorgan.validators.jodatime.Annotations.getDefaultValueAsInt;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("deprecation")
public class BeforeValidatorForReadableInstant implements ConstraintValidator<Before, ReadableInstant> {

    private ReadablePartial partial;

    @Override
    public void initialize(@Nonnull Before constraintAnnotation) {
        checkNotNull(constraintAnnotation, "constraintAnnotation");

        Partial partial = new Partial();

        try {
            if (constraintAnnotation.year() != getDefaultValueAsInt(Before.class, "year")) {
                partial = partial.with(DateTimeFieldType.yearOfEra(), constraintAnnotation.year());
            }
            if (constraintAnnotation.month() != getDefaultValueAsInt(Before.class, "month")) {
                partial = partial.with(DateTimeFieldType.monthOfYear(), constraintAnnotation.month());
            }
            if (constraintAnnotation.day() != getDefaultValueAsInt(Before.class, "day")) {
                partial = partial.with(DateTimeFieldType.dayOfMonth(), constraintAnnotation.day());
            }
            if (constraintAnnotation.hour() != getDefaultValueAsInt(Before.class, "hour")) {
                partial = partial.with(DateTimeFieldType.hourOfDay(), constraintAnnotation.hour());
            }
            if (constraintAnnotation.minute() != getDefaultValueAsInt(Before.class, "minute")) {
                partial = partial.with(DateTimeFieldType.minuteOfHour(), constraintAnnotation.minute());
            }
            if (constraintAnnotation.second() != getDefaultValueAsInt(Before.class, "second")) {
                partial = partial.with(DateTimeFieldType.secondOfMinute(), constraintAnnotation.second());
            }
            if (constraintAnnotation.millis() != getDefaultValueAsInt(Before.class, "millis")) {
                partial = partial.with(DateTimeFieldType.millisOfSecond(), constraintAnnotation.millis());
            }
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
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