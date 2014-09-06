package com.github.hamishmorgan.jodatimevalidators;

import com.google.common.base.Supplier;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validator to constrain {@link ReadableInstant} in the past. Verified to work with all known concrete
 * implements: {@link org.joda.time.DateTime}, {@link org.joda.time.DateMidnight}, and
 * {@link org.joda.time.MutableDateTime}.
 * <p/>
 * Null instants are considered valid. The {@code DateMidnight} value for today is consider in the past; hence svalid.
 */
@SuppressWarnings("deprecation")
public class PastValidatorForReadableInstant implements ConstraintValidator<Past, ReadableInstant> {

    @Nonnull
    private Supplier<DateTime> currentDateTimeSupplier = new Supplier<DateTime>() {
        @Override
        public DateTime get() {
            return new DateTime();
        }
    };

    @Override
    public void initialize(@Nonnull Past constraintAnnotation) {
        checkNotNull(constraintAnnotation, "constraintAnnotation");
    }

    @Override
    public boolean isValid(@Nullable ReadableInstant value, @Nonnull ConstraintValidatorContext context) {
        checkNotNull(context, "context");
        return value == null || value.isBefore(currentDateTimeSupplier.get());
    }
}