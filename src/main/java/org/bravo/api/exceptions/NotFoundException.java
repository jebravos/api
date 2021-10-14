package org.bravo.api.exceptions;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public static Runnable throwNotFoundException(String message) {
        return () -> {
            throw new NotFoundException(message);
        };
    }
    public static Supplier<NotFoundException> newNotFoundException(String message) {
        return () -> {
            throw new NotFoundException(message);
        };
    }
}
