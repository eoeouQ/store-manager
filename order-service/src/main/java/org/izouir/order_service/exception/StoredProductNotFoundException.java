package org.izouir.order_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class StoredProductNotFoundException extends EntityNotFoundException {
    public StoredProductNotFoundException(final String message) {
        super(message);
    }
}
