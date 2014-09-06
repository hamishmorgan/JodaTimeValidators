package com.github.hamishmorgan.jodatimevalidators;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BeforeValidatorForReadableInstantTest {

    @InjectMocks
    private BeforeValidatorForReadableInstant validator;

    @Mock
    private Before constraintAnnotation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @org.junit.Before
    public void initializeMocks() throws NoSuchMethodException {
        when(constraintAnnotation.year()).thenReturn(
                (Integer) Before.class.getMethod("year").getDefaultValue());
    }

    @Test
    public void givenValidConstraintAnnotation_whenInitialize_thenNotExcpetionThrown() {
        try {
            validator.initialize(constraintAnnotation);
        } catch (Throwable t) {
            fail("Expected no exception", t);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void givenNullConstraintAnnotation_whenInitialize_thenThrowsNPE() {
        validator.initialize(null);
    }

    @Test
    public void givenNullConstraintAnnotation_whenInitialize_thenAccessesYears() {
        validator.initialize(constraintAnnotation);
        verify(constraintAnnotation).year();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void givenNullConstraintValidatorContext_whenIsValid_thenNPE() {
        validator.isValid(null, null);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void givenNullInstant_whenIsValid_thenReturnTrue() {
        ReadableInstant instant = null;
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenValueIsEndOfPreviousYear_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.year()).thenReturn(2006);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 12, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenValueIsBeginningOfIsSameYear_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.year()).thenReturn(2006);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2006, 1, 1, 0, 0, 1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenValueIsEndOfSameYear_whenIsValid_thenReturnFalse2() {
        when(constraintAnnotation.year()).thenReturn(2006);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2006, 12, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenValueIsBeginningOfNextYear_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.year()).thenReturn(2006);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2007, 1, 1, 0, 0, 1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearUnset_whenIsValid_thenReturnTrue() throws NoSuchMethodException {
        when(constraintAnnotation.year()).thenReturn(2006);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2007, 1, 1, 0, 0, 1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

}