package com.github.shiranr.scooters.exceptions;

/**
 * This exception happens when a user is trying to change a scooter status to the status it is currently on
 */
public class InvalidStateException extends IllegalArgumentException {

    public InvalidStateException(String message) {
        super(message);
    }

}
