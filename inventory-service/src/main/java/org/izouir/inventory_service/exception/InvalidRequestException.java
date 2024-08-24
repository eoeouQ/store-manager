package org.izouir.inventory_service.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(final String message) {
        super(message);
    }
}
