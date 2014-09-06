package com.github.hamishmorgan.jodatimevalidators;


import com.github.hamishmorgan.jodatimevalidators.fixtures.Contact;
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
import javax.validation.constraints.Past;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactValidationTest {

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
    public void givenContactWithPastDateOfBirth_whenValidate_thenCausesNoConstraintViolation() {
        Contact contact = new Contact(new DateMidnight(1960, 11, 3));
        Set<ConstraintViolation<Contact>> constraintViolations = validator.validate(contact);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void givenContactWithNullDateOfBirth_whenValidate_thenCausesNoConstraintViolation() {
        Contact contact = new Contact(null);
        Set<ConstraintViolation<Contact>> constraintViolations = validator.validate(contact);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void givenContactWithFutureDateOfBirth_whenValidate_thenCausesSingleConstraintViolation() {
        Contact contact = new Contact(new DateMidnight(2020, 11, 3));
        Set<ConstraintViolation<Contact>> constraintViolations = validator.validate(contact);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    public void givenContactWithFutureDateOfBirth_whenValidate_thenCausesPastConstraintViolation() {
        Contact contact = new Contact(new DateMidnight(2020, 11, 3));
        ConstraintViolation<Contact> constraintViolations = Iterables.getOnlyElement(validator.validate(contact));
        assertThat(constraintViolations.getConstraintDescriptor().getAnnotation()).isInstanceOf(Past.class);
    }

}