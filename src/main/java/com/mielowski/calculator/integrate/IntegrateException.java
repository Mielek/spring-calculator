package com.mielowski.calculator.integrate;

public class IntegrateException extends RuntimeException {
    public IntegrateException() {
    }

    public IntegrateException(String message) {
        super(message);
    }

    public IntegrateException(String message, Throwable cause) {
        super(message, cause);
    }
}
