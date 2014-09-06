package com.github.hamishmorgan.validators.jodatime;

import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validator to constrain {@link org.joda.time.ReadableInstant} in the past. Verified to work with all known concrete
 * implements: {@link org.joda.time.LocalDate}, {@link org.joda.time.LocalDateTime}, {@link org.joda.time.LocalTime},
 * , {@link org.joda.time.MonthDay}, {@link org.joda.time.TimeOfDay}, {@link org.joda.time.YearMonth},
 * {@link org.joda.time.YearMonthDay}
 * <p/>
 * Null instants are considered valid.
 * <p/>
 * Missing temporal fields are populated from the current date and time.
 */
@SuppressWarnings("deprecation")
public class PastValidatorForReadablePartial extends AbstractCurrentTimeRelativeValidator<Past, ReadablePartial> {

    @Override
    public boolean isValid(@Nullable ReadablePartial value, @Nonnull ConstraintValidatorContext context) {
        checkNotNull(context, "context");
        if (value == null) {
            return true;
        }
        final ReadableInstant now = getCurrentDateTime();
        return value.toDateTime(now).isBefore(now);
    }
}