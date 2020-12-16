package com.github.shiranr.scooters.domain;

/**
 * History of a scooter is composed from check in and out of the scooter by a user
 */
public class HistoryRecord {
    long checkedIn;
    long checkedOut;

    public HistoryRecord(long checkedIn, long checkedOut) {
        this.checkedIn = checkedIn;
        this.checkedOut = checkedOut;
    }
}
