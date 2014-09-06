package com.github.hamishmorgan.jodatimevalidators;

import org.joda.time.ReadableInterval;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validator to constrain {@link org.joda.time.ReadableInterval} in the past.
 * <p/>
 * Both the start and end instants must be in the past. Verified to work with all known concrete implements:
 * {@link org.joda.time.Interval}, and {@link org.joda.time.MutableInterval}. Null instants are considered valid.
 */
public class PastValidatorForReadableInterval extends AbstractCurrentTimeRelativeValidator<Past, ReadableInterval> {

    @Override
    public boolean isValid(@Nullable ReadableInterval value, @Nonnull ConstraintValidatorContext context) {
        checkNotNull(context, "context");
        return value == null || value.isBefore(getCurrentDateTime());
    }
}