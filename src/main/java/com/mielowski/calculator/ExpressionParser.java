package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private String expression;
    private int currentPosition = -1;
    private int currentCharacter;
    private int lastOperationCharacter;

    public ExpressionParser(String expression) {
        this.expression = expression.trim().toLowerCase();
    }

    public Expression parse() {
        throwIfExpressionIsEmpty();
        nextChar();
        Expression x = parseExpression();
        throwIfUnknownEnding();
        return x;
    }

    private void throwIfExpressionIsEmpty() {
        if (expression.isEmpty())
            throw new RuntimeException("Expression is empty");
    }

    private void throwIfUnknownEnding() {
        if (currentPosition < expression.length())
            throw new RuntimeException("Unexpected expression ending " + expression.substring(currentPosition));
    }

    private Expression parseExpression() {
        return createSubExpression(this::parseTerm, '+', '-');
    }

    private Expression parseTerm() {
        return createSubExpression(this::parseFactor, '*', '/');
    }

    private Expression createSubExpression(Supplier<Expression> nextParser, int... allowedOperations) {
        Expression left = nextParser.get();
        while (isNextOperation(allowedOperations)) {
            left = createBinaryExpression(lastOperationCharacter, left, nextParser.get());
        }
        return left;
    }

    private Expression parseFactor() {
        if (isNextOperation('+', '-'))
            return createUnaryExpression(lastOperationCharacter, parseFactor());

        if (isNextParentheses())
            return parseParenthesesFactor();

        if (isNumberCharacter((char) currentCharacter))
            return parseConstantFactor();

        if (isAlphabetCharacter((char) currentCharacter))
            return parseFunctionFactor();

        throw new RuntimeException("Unexpected: " + (char) currentCharacter);
    }

    private boolean isNextParentheses() {
        return isNextOperation('(', '[', '{');
    }

    private Expression parseParenthesesFactor() {
        char ending = getEndingParentheses(lastOperationCharacter);
        Expression x = parseExpression();
        if (!eat(ending))
            throw new RuntimeException("No ending parenthesis: " + ending);
        return x;
    }

    private boolean isNumberCharacter(int currentCharacter) {
        return isCharacterBetweenValues(currentCharacter, '0', '9') || currentCharacter == '.';
    }

    private Expression parseConstantFactor() {
        String number = buildValue(this::isNumberCharacter);
        return ConstantExpression.of(Double.parseDouble(number));
    }

    private boolean isAlphabetCharacter(int currentCharacter) {
        return isCharacterBetweenValues(currentCharacter, 'a', 'z');
    }

    private boolean isCharacterBetweenValues(int test, int left, int right) {
        return test >= left && test <= right;
    }

    private Expression parseFunctionFactor() {
        String func = buildValue(this::isAlphabetCharacter);
        Expression x = parseFactor();
        if (func.equals("sqrt")) x = SquareExpression.of(x);
        else if (func.equals("root")) x = SquareRootExpression.of(x);
        else throw new RuntimeException("Unknown function: " + func);
        return x;
    }

    private String buildValue(IntPredicate predicate){
        StringBuilder builder = new StringBuilder();
        while (predicate.test(currentCharacter)) {
            builder.append((char) currentCharacter);
            nextChar();
        }
        return builder.toString();
    }

    private char getEndingParentheses(int parenthesis) {
        switch (parenthesis) {
            case '(':
                return ')';
            case '[':
                return ']';
            case '{':
                return '}';
            default:
                throw new RuntimeException("Unknown ending parentheses: " + parenthesis);
        }
    }

    private Expression createUnaryExpression(int unaryOperator, Expression unary) {
        switch (unaryOperator) {
            case '+':
                return unary;
            case '-':
                return NegativeExpression.of(unary);
            default:
                throw new RuntimeException("Unknown unary operation under char: " + unaryOperator);
        }
    }

    private Expression createBinaryExpression(int binaryOperator, Expression left, Expression right) {
        switch (binaryOperator) {
            case '+':
                return AdditionExpression.of(left, right);
            case '-':
                return SubtractionExpression.of(left, right);
            case '*':
                return MultiplyExpression.of(left, right);
            case '/':
                return DivisionExpression.of(left, right);
            default:
                throw new RuntimeException("Unknown operation under char: " + binaryOperator);
        }
    }

    private boolean isNextOperation(int... operations) {
        skipWhiteCharacters();
        if (IntStream.of(operations).anyMatch(value -> value == currentCharacter)) {
            lastOperationCharacter = currentCharacter;
            nextChar();
            return true;
        }
        return false;
    }

    private void skipWhiteCharacters() {
        skipCharacters(value -> value == ' ');
    }

    private void skipCharacters(IntPredicate predicate) {
        while (predicate.test(currentCharacter)) nextChar();
    }

    private int nextChar() {
        return currentCharacter = (++currentPosition < expression.length()) ? expression.charAt(currentPosition) : -1;
    }

    private boolean eat(int charToEat) {
        skipWhiteCharacters();
        if (currentCharacter == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }
}
