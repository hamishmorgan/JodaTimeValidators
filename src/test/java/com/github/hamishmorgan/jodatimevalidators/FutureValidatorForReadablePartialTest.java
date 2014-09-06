package com.github.hamishmorgan.jodatimevalidators;

import com.google.common.base.Supplier;
import org.joda.time.*;
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
public class FutureValidatorForReadablePartialTest {

    @Mock
    private Supplier<DateTime> currentDateTimeSupplier;

    @InjectMocks
    private FutureValidatorForReadablePartial validator;

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
    public void givenNullReadablePartial_whenIsValid_thenReturnTrue() {
        ReadablePartial instant = null;
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenLocalDateIsInThePast_whenIsValid_thenReturnFalse() {
        LocalDate instant = currentDateTimeSupplier.get().toLocalDate().minusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenLocalDateIsNow_whenIsValid_thenReturnFalse() {
        LocalDate instant = currentDateTimeSupplier.get().toLocalDate();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenLocalDateIsInTheFuture_whenIsValid_thenReturnTrue() {
        LocalDate instant = currentDateTimeSupplier.get().toLocalDate().plusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenLocalDateTimeIsInThePast_whenIsValid_thenReturnFalse() {
        LocalDateTime instant = currentDateTimeSupplier.get().toLocalDateTime().minusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenLocalDateTimeIsNow_whenIsValid_thenReturnFalse() {
        LocalDateTime instant = currentDateTimeSupplier.get().toLocalDateTime();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenLocalDateTimeIsInTheFuture_whenIsValid_thenReturnTrue() {
        LocalDateTime instant = currentDateTimeSupplier.get().toLocalDateTime().plusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenLocalTimeIsInThePast_whenIsValid_thenReturnFalse() {
        LocalTime instant = currentDateTimeSupplier.get().toLocalTime().minusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenDateTimeIsNow_whenIsValid_thenReturnFalse() {
        LocalTime instant = currentDateTimeSupplier.get().toLocalTime();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenDateTimeIsInTheFuture_whenIsValid_thenReturnTrue() {
        LocalTime instant = currentDateTimeSupplier.get().toLocalTime().plusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenMonthDayIsInThePast_whenIsValid_thenReturnFalse() {
        DateTime now = currentDateTimeSupplier.get();
        MonthDay instant = new MonthDay(now.getMonthOfYear(), now.getDayOfMonth()).minusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMonthDayIsNow_whenIsValid_thenReturnFalse() {
        DateTime now = currentDateTimeSupplier.get();
        MonthDay instant = new MonthDay(now.getMonthOfYear(), now.getDayOfMonth());
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenMonthDayIsInTheFuture_whenIsValid_thenReturnTrue() {
        DateTime now = currentDateTimeSupplier.get();
        MonthDay instant = new MonthDay(now.getMonthOfYear(), now.getDayOfMonth()).plusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenTimeOfDayIsInThePast_whenIsValid_thenReturnFalse() {
        TimeOfDay instant = currentDateTimeSupplier.get().toTimeOfDay().minusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenTimeOfDayIsNow_whenIsValid_thenReturnFalse() {
        TimeOfDay instant = currentDateTimeSupplier.get().toTimeOfDay();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenTimeOfDayIsInTheFuture_whenIsValid_thenReturnTrue() {
        TimeOfDay instant = currentDateTimeSupplier.get().toTimeOfDay().plusSeconds(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenYearMonthIsInThePast_whenIsValid_thenReturnFalse() {
        DateTime now = currentDateTimeSupplier.get();
        YearMonth instant = new YearMonth(now.getYear(), now.getMonthOfYear()).minusMonths(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearMonthIsNow_whenIsValid_thenReturnFalse() {
        DateTime now = currentDateTimeSupplier.get();
        YearMonth instant = new YearMonth(now.getYear(), now.getMonthOfYear());
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearMonthIsInTheFuture_whenIsValid_thenReturnTrue() {
        DateTime now = currentDateTimeSupplier.get();
        YearMonth instant = new YearMonth(now.getYear(), now.getMonthOfYear()).plusMonths(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

    @Test
    public void givenYearMonthDayIsInThePast_whenIsValid_thenReturnFalse() {
        YearMonthDay instant = currentDateTimeSupplier.get().toYearMonthDay().minusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearMonthDayIsNow_whenIsValid_thenReturnFalse() {
        YearMonthDay instant = currentDateTimeSupplier.get().toYearMonthDay();
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isFalse();
    }

    @Test
    public void givenYearMonthDayIsInTheFuture_whenIsValid_thenReturnTrue() {
        YearMonthDay instant = currentDateTimeSupplier.get().toYearMonthDay().plusDays(1);
        boolean valid = validator.isValid(instant, constraintValidatorContext);
        assertThat(valid).isTrue();
    }

}