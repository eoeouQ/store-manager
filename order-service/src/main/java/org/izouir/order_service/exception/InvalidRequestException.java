package org.izouir.order_service.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(final String message) {
        super(message);
    }
}
