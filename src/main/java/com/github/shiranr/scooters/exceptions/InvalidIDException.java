package com.github.shiranr.scooters.exceptions;

/**
 * This exception happens when the ID we received is invalid.
 */
public class InvalidIDException extends IllegalArgumentException {

    public InvalidIDException(String s) {
        super(s);
    }

}
