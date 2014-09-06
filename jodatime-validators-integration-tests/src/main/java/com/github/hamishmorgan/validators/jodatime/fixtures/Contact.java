package com.github.hamishmorgan.validators.jodatime.fixtures;

import org.joda.time.ReadableInstant;

import javax.validation.constraints.Past;

public class Contact {

    @Past
    private final ReadableInstant dateOfBirth;

    public Contact(ReadableInstant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}