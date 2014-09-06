package com.github.hamishmorgan.validators.jodatime;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.ReadableInstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static com.github.hamishmorgan.validators.jodatime.Annotations.getDefaultValueAsInt;
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
        when(constraintAnnotation.year()).thenReturn(getDefaultValueAsInt(Before.class, "year"));
        when(constraintAnnotation.month()).thenReturn(getDefaultValueAsInt(Before.class, "month"));
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

    @Test(expected = IllegalFieldValueException.class)
    public void givenMonthGreaterThan12_whenInitialize_thenThrowIllegalFieldValueException() {
        when(constraintAnnotation.month()).thenReturn(13);
        validator.initialize(constraintAnnotation);
    }

    @Test(expected = IllegalFieldValueException.class)
    public void givenMonthLessThanZero_whenInitialize_thenThrowIllegalFieldValueException() {
        when(constraintAnnotation.month()).thenReturn(-1);
        validator.initialize(constraintAnnotation);
    }

    @Test
    public void givenMonthIsBefore_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.month()).thenReturn(6);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMonthIsEqual_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.month()).thenReturn(5);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 1, 1, 0, 0);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMonthIsAfter_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.month()).thenReturn(4);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 1, 1, 0, 0);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


    @Test
    public void givenYearIsBefore_andMonthIsAfter_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.year()).thenReturn(2006);
        when(constraintAnnotation.month()).thenReturn(4);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }


    @Test
    public void givenYearIsAfter_andMonthIsBefore_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.year()).thenReturn(2004);
        when(constraintAnnotation.month()).thenReturn(6);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearIsEqual_andMonthIsBefore_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.year()).thenReturn(2005);
        when(constraintAnnotation.month()).thenReturn(6);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenYearIsEqual_andMonthIsAfter_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.year()).thenReturn(2005);
        when(constraintAnnotation.month()).thenReturn(4);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearIsEqual_andMonthIsEqual_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.year()).thenReturn(2005);
        when(constraintAnnotation.month()).thenReturn(5);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 31, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


    @Test(expected = IllegalFieldValueException.class)
    public void givenDayGreaterThan31_whenInitialize_thenThrowIllegalFieldValueException() {
        when(constraintAnnotation.day()).thenReturn(32);
        validator.initialize(constraintAnnotation);
    }

    @Test(expected = IllegalFieldValueException.class)
    public void givenDayLessThanZero_whenInitialize_thenThrowIllegalFieldValueException() {
        when(constraintAnnotation.day()).thenReturn(-1);
        validator.initialize(constraintAnnotation);
    }

    @Test
    public void givenDayIsBefore_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.day()).thenReturn(16);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 15, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenDayIsEqual_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.day()).thenReturn(15);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 15, 1, 0, 0);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenDayIsAfter_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.day()).thenReturn(14);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 15, 1, 0, 0);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


    @Test
    public void givenMonthIsBefore_andDayIsAfter_whenIsValid_thenReturnTrue() {
        when(constraintAnnotation.month()).thenReturn(6);
        when(constraintAnnotation.day()).thenReturn(14);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 15, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMonthIsAfter_andDayIsBefore_whenIsValid_thenReturnFalse() {
        when(constraintAnnotation.month()).thenReturn(4);
        when(constraintAnnotation.day()).thenReturn(16);
        validator.initialize(constraintAnnotation);
        DateTime instant = new DateTime(2005, 5, 15, 23, 59, 59);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


}