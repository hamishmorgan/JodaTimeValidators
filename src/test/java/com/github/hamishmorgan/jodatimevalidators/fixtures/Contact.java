package com.github.hamishmorgan.jodatimevalidators.fixtures;

import javax.validation.constraints.Past;

import org.joda.time.DateMidnight;

public class Contact {

    @Past
    private final DateMidnight dateOfBirth;

    public Contact(DateMidnight dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}