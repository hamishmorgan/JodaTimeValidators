package com.github.hamishmorgan.jodatimevalidators;

import com.google.common.base.Supplier;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableInstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PastValidatorForReadableInstantTest {

    @Mock
    private Supplier<DateTime> currentDateTimeSupplier;

    @InjectMocks
    private PastValidatorForReadableInstant validator;

    @Mock
    private Past constraintAnnotation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void initializeMocks() {
        DateTime fixedDateTime = new DateTime(2014, 9, 6, 10, 17, 1, 0);
        when(currentDateTimeSupplier.get()).thenReturn(fixedDateTime);
    }

    @Test
    public void whenDefaultConstructorCalled_thenNoExceptionThrown() {
        new PastValidatorForReadableInstant();
    }

    @Test
    public void givenValidConstraintAnnotation_whenInitialize_thenZeroInteractions() {
        validator.initialize(constraintAnnotation);
        verifyZeroInteractions(constraintAnnotation);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void givenNullConstraintAnnotation_whenInitialize_thenThrowsNPE() {
        validator.initialize(null);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void givenNullConstraintValidatorContext_whenIsValid_thenNPE() {
        validator.isValid(null, null);
    }

    @Test
    public void givenValidConstraintValidatorContext_whenIsValid_thenZeroInteractions() {
        validator.isValid(null, constraintValidatorContext);
        verifyZeroInteractions(constraintAnnotation);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void givenNullInstant_whenIsValid_thenReturnTrue() {
        ReadableInstant instant = null;
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenDateTimeIsInThePast_whenIsValid_thenReturnTrue() {
        DateTime instant = currentDateTimeSupplier.get().minusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenDateTimeIsNow_whenIsValid_thenReturnFalse() {
        DateTime instant = currentDateTimeSupplier.get();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenDateTimeIsInTheFuture_whenIsValid_thenReturnFalse() {
        DateTime instant = currentDateTimeSupplier.get().plusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMutableDateTimeIsInThePast_whenIsValid_thenReturnTrue() {
        MutableDateTime instant = currentDateTimeSupplier.get().minusSeconds(1).toMutableDateTime();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMutableDateTimeIsNow_whenIsValid_thenReturnFalse() {
        MutableDateTime instant = currentDateTimeSupplier.get().toMutableDateTime();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMutableDateTimeIsInTheFuture_whenIsValid_thenReturnFalse() {

        MutableDateTime instant = currentDateTimeSupplier.get().plusSeconds(1).toMutableDateTime();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void givenDateMidnightIsInThePast_whenIsValid_thenReturnTrue() {
        DateMidnight instant = currentDateTimeSupplier.get().toDateMidnight().minusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void givenDateMidnightToday_whenIsValid_thenReturnTrue() {
        DateMidnight instant = currentDateTimeSupplier.get().toDateMidnight();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void givenDateMidnightIsInTheFuture_whenIsValid_thenReturnFalse() {
        DateMidnight instant = currentDateTimeSupplier.get().toDateMidnight().plusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

}