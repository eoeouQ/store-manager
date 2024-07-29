package org.izouir.order_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(final String message) {super(message);}
}
