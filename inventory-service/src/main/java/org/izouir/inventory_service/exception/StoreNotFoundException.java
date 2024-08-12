package org.izouir.inventory_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class StoreNotFoundException extends EntityNotFoundException {
    public StoreNotFoundException(final String message) {
        super(message);
    }
}
