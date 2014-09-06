package com.github.hamishmorgan.jodatimevalidators.fixtures;

import org.joda.time.ReadableInstant;

import javax.validation.constraints.Future;

public class CreditCard {

    @Future
    private final ReadableInstant expiryDate;

    public CreditCard(ReadableInstant expiryDate) {
        this.expiryDate = expiryDate;
    }

}