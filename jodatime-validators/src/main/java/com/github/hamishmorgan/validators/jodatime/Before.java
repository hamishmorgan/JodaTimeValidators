package com.github.hamishmorgan.validators.jodatime;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a joda time type whose value must be lower than the specified value
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface Before {


    String message() default "{com.github.hamishmorgan.validators.jodatime.Before.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int year() default Integer.MIN_VALUE;

    int month() default 0;

    /**
     * Defines several {@link Before} annotations on the same element.
     *
     * @see Before
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Before[] value();
    }
}
