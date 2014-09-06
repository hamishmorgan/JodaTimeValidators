package com.github.hamishmorgan.jodatimevalidators;


import com.github.hamishmorgan.jodatimevalidators.fixtures.CreditCard;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidationTest {

    @InjectMocks
    private static Validator validator;

    @Mock
    private Supplier<DateTime> currentDateTimeSupplier;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Before
    public void setupMocks() {
        DateTime currentDateTime = new DateTime(2014, 9, 6, 10, 17, 1, 0);
        when(currentDateTimeSupplier.get()).thenReturn(currentDateTime);
    }

    @Test
    public void givenCreditCardWithFutureExpiryDate_whenValidate_thenCausesNoConstraintViolation() {
        CreditCard creditCard = new CreditCard(new DateMidnight(2020, 11, 3));
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(creditCard);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void givenCreditCardWithNullExpiryDate_whenValidate_thenCausesNoConstraintViolation() {
        CreditCard creditCard = new CreditCard(null);
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(creditCard);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void givenCreditCardWithPastExpiryDate_whenValidate_thenCausesSingleConstraintViolation() {
        CreditCard creditCard = new CreditCard(new DateMidnight(1960, 11, 3));
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(creditCard);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    public void givenCreditCardWithPastExpiryDate_whenValidate_thenCausesFutureConstraintViolation() {
        CreditCard creditCard = new CreditCard(new DateMidnight(1960, 11, 3));
        ConstraintViolation<CreditCard> constraintViolations = Iterables.getOnlyElement(validator.validate(creditCard));
        assertThat(constraintViolations.getConstraintDescriptor().getAnnotation()).isInstanceOf(Future.class);
    }

}