package com.github.hamishmorgan.validators.jodatime;

import com.google.common.base.Supplier;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

abstract class AbstractCurrentTimeRelativeValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

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