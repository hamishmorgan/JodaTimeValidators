package com.github.hamishmorgan.validators.jodatime;

import org.joda.time.ReadableInstant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validator to constrain {@link ReadableInstant} in the past. Verified to work with all known concrete
 * implements: {@link org.joda.time.DateTime}, {@link org.joda.time.DateMidnight}, and
 * {@link org.joda.time.MutableDateTime}.
 * <p/>
 * Null instants are considered valid.
 * <p/>
 * The {@code DateMidnight} value for today is consider in the past; hence valid.
 */
@SuppressWarnings("deprecation")
public class PastValidatorForReadableInstant extends AbstractCurrentTimeRelativeValidator<Past, ReadableInstant> {

    @Override
    public boolean isValid(@Nullable ReadableInstant value, @Nonnull ConstraintValidatorContext context) {
        checkNotNull(context, "context");
        return value == null || value.isBefore(getCurrentDateTime());
    }
}