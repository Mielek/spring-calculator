package com.mielowski.calculator;

public class ExpressionParserException extends RuntimeException {
    public ExpressionParserException() {
    }

    public ExpressionParserException(String message) {
        super(message);
    }

    public ExpressionParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
