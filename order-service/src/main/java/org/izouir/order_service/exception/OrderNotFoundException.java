package org.izouir.order_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(final String message) {super(message);}
}
