package com.github.hamishmorgan.validators.jodatime.fixtures;

import org.joda.time.ReadableInstant;

import javax.validation.constraints.Future;

public class CreditCard {

    @Future
    private final ReadableInstant expiryDate;

    public CreditCard(ReadableInstant expiryDate) {
        this.expiryDate = expiryDate;
    }

}