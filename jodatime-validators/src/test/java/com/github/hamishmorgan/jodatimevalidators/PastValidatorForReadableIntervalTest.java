package com.github.hamishmorgan.jodatimevalidators;

import com.google.common.base.Supplier;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.MutableInterval;
import org.joda.time.ReadableInterval;
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
public class PastValidatorForReadableIntervalTest {

    @Mock
    private Supplier<DateTime> currentDateTimeSupplier;

    @InjectMocks
    private PastValidatorForReadableInterval validator;

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
    public void givenNullInterval_whenIsValid_thenReturnTrue() {
        ReadableInterval instant = null;
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenIntervalStartAndEndAreInThePast_whenIsValid_thenReturnTrue() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get().minusSeconds(2),
                currentDateTimeSupplier.get().minusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenIntervalStartIsInThePastAndEndIsNow_whenIsValid_thenReturnTrue() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get().minusSeconds(1),
                currentDateTimeSupplier.get());
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenIntervalStartIsNowAndEndIsInTheFuture_whenIsValid_thenReturnFalse() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get(),
                currentDateTimeSupplier.get().plusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenIntervalStartAndEndIsNow_whenIsValid_thenReturnTrue() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get(),
                currentDateTimeSupplier.get());
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenIntervalStartInThePatAndEndIsInTheFuture_whenIsValid_thenReturnFalse() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get().minusSeconds(1),
                currentDateTimeSupplier.get().plusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenIntervalStartAndEndInTheFuture_whenIsValid_thenReturnFalse() {
        Interval interval = new Interval(
                currentDateTimeSupplier.get().plusSeconds(1),
                currentDateTimeSupplier.get().plusSeconds(2));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


    @Test
    public void givenMutableIntervalStartAndEndAreInThePast_whenIsValid_thenReturnTrue() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get().minusSeconds(2),
                currentDateTimeSupplier.get().minusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMutableIntervalStartIsInThePastAndEndIsNow_whenIsValid_thenReturnTrue() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get().minusSeconds(1),
                currentDateTimeSupplier.get());
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMutableIntervalStartIsNowAndEndIsInTheFuture_whenIsValid_thenReturnFalse() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get(),
                currentDateTimeSupplier.get().plusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMutableIntervalStartAndEndIsNow_whenIsValid_thenReturnTrue() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get(),
                currentDateTimeSupplier.get());
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMutableIntervalStartInThePatAndEndIsInTheFuture_whenIsValid_thenReturnFalse() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get().minusSeconds(1),
                currentDateTimeSupplier.get().plusSeconds(1));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMutableIntervalStartAndEndInTheFuture_whenIsValid_thenReturnFalse() {
        MutableInterval interval = new MutableInterval(
                currentDateTimeSupplier.get().plusSeconds(1),
                currentDateTimeSupplier.get().plusSeconds(2));
        boolean valid = validator.isValid(interval, constraintValidatorContext);
        assertThat(valid).isFalse();
    }


}