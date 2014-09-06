package com.github.hamishmorgan.jodatimevalidators;

import com.google.common.base.Supplier;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import javax.annotation.Nonnull;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

abstract class AbstractCurrentTimeRelativeValidatorForReadableInstant<A extends Annotation>
        implements ConstraintValidator<A, ReadableInstant> {

    @Nonnull
    private Supplier<DateTime> currentDateTimeSupplier = new Supplier<DateTime>() {
        @Override
        public DateTime get() {
            return new DateTime();
        }
    };

    @Nonnull
    final DateTime getCurrentDateTime() {
        return currentDateTimeSupplier.get();
    }

    @Override
    public void initialize(@Nonnull A constraintAnnotation) {
        checkNotNull(constraintAnnotation, "constraintAnnotation");
    }

}