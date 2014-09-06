package com.github.hamishmorgan.jodatimevalidators.fixtures;

import org.joda.time.DateMidnight;

import javax.validation.constraints.Past;

public class Contact {

    @Past
    private final DateMidnight birthDay;

    public Contact(DateMidnight birthDay) {
        this.birthDay = birthDay;
    }

}