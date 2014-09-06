package com.github.hamishmorgan.jodatimevalidators.fixtures;

import org.joda.time.ReadableInstant;

import javax.validation.constraints.Past;

public class Contact {

    @Past
    private final ReadableInstant dateOfBirth;

    public Contact(ReadableInstant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}