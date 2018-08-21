package com.mielowski.calculator.expression;

import java.util.function.BooleanSupplier;

public class ExpressionTokenizer {
    private String expression;
    private int currentPosition = -1;
    private char currentCharacter;
    private Token token;

    ExpressionTokenizer(String expression) {
        this.expression = expression.trim().replace(" ", "").toLowerCase();
        nextCharacter();
        nextToken();
    }

    public Token getToken() {
        return token;
    }

    public Token nextToken(){
        if(isValueCharacter())
            token = getValue();
        else if (isFunctionCharacter())
            token = getFunction();
        else if(isEndingCharacter())
            token = getOperation();
        else
            token = new Token("", TokenType.END);
        return token;
    }

    private boolean isEndingCharacter() {
        return currentCharacter != '\0';
    }

    public Token getTokenAndMove(){
        Token tmp = token;
        nextToken();
        return tmp;
    }

    private Token getOperation() {
        Token t = new Token(String.valueOf(currentCharacter), TokenType.OPERATION);
        nextCharacter();
        return t;
    }

    private void nextCharacter() {
        currentCharacter = ((++currentPosition < expression.length()) ? expression.charAt(currentPosition) : '\0');
    }

    boolean isEmpty(){
        return expression.isEmpty();
    }

    boolean hasNext() {
        return !token.isEnd();
    }

    private Token getValue() {
        return new Token(getAllUntil(this::isValueCharacter), TokenType.VALUE);
    }

    private Token getFunction() {
        return new Token(getAllUntil(this::isFunctionCharacter), TokenType.FUNCTION);
    }

    private String getAllUntil(BooleanSupplier test) {
        StringBuilder builder = new StringBuilder();
        while (test.getAsBoolean()) {
            builder.append(currentCharacter);
            nextCharacter();
        }
        return builder.toString();
    }

    public String getUnconsumedString() {
        return token.getValue() + expression.substring(currentPosition);
    }

    private boolean isFunctionCharacter() {
        return isCharacterBetweenValues(currentCharacter, 'a', 'z');
    }

    private boolean isValueCharacter() {
        return isCharacterBetweenValues(currentCharacter, '0', '9') || currentCharacter == '.';
    }

    private boolean isCharacterBetweenValues(int test, int left, int right) {
        return test >= left && test <= right;
    }


    private enum TokenType { OPERATION, FUNCTION, VALUE, END }

    public static class Token {
        String value;
        TokenType type;

        private Token(String value, TokenType type) {
            this.value = value;
            this.type = type;
        }

        public boolean isOperation(){
            return TokenType.OPERATION.equals(type);
        }

        public boolean isFunction(){
            return TokenType.FUNCTION.equals(type);
        }

        public boolean isValue(){
            return TokenType.VALUE.equals(type);
        }

        public boolean isEnd() {
            return TokenType.END.equals(type);
        }

        public String getValue() {
            return value;
        }
    }
}
