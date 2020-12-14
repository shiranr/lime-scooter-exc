package com.github.shiranr.scooters.domain.internal;

/**
 * History of a scooter is composed from check in and out of the scooter by a user
 */
public class History {
    long checkedIn;
    long checkedOut;

    public History(long checkedIn, long checkedOut) {
        this.checkedIn = checkedIn;
        this.checkedOut = checkedOut;
    }
}
