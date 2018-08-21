package com.mielowski.calculator.expression;

import java.util.Collection;
import java.util.function.BooleanSupplier;
import java.util.stream.IntStream;

public class ExpressionTokenizer {
    private String expression;
    private int currentPosition = -1;
    private char currentToken;

    public ExpressionTokenizer(String expression) {
        this.expression = expression.trim().replace(" ", "").toLowerCase();
        nextToken();
    }

    public boolean hasNext() {
        return currentPosition < expression.length();
    }

    public void nextToken() {
        currentToken = (char)((++currentPosition < expression.length()) ? expression.charAt(currentPosition) : -1);
    }

    public char getCurrentToken() {
        return currentToken;
    }

    public String getValue() {
        return getAllUntil(this::isValueToken);
    }


    public String getFunction() {
        return getAllUntil(this::isFunctionToken);
    }

    private String getAllUntil(BooleanSupplier test) {
        StringBuilder builder = new StringBuilder();
        while (test.getAsBoolean()) {
            builder.append((char) currentToken);
            nextToken();
        }
        return builder.toString();
    }

    public String getUnconsumedString() {
        return expression.substring(currentPosition);
    }

    public boolean isFunctionToken() {
        return isCharacterBetweenValues(currentToken, 'a', 'z');
    }

    public boolean isValueToken() {
        return isCharacterBetweenValues(currentToken, '0', '9') || currentToken == '.';
    }

    private boolean isCharacterBetweenValues(int test, int left, int right) {
        return test >= left && test <= right;
    }

    public boolean isCurrentAnOperation(int... operations) {
        return IntStream.of(operations).anyMatch(operation -> operation == currentToken);
    }

    public boolean isCurrentAnOperation(Collection<Character> operations){
        return operations.stream().anyMatch(operation -> operation == currentToken);
    }

    public char getCurrentAndMove() {
        char last = currentToken;
        nextToken();
        return last;
    }
}
