package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
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
        if (expression.isEmpty())
            throw new RuntimeException("Expression is empty");
        nextChar();
        Expression x = parseExpression();
        if (currentPosition < expression.length())
            throw new RuntimeException("Unexpected expression ending " + expression.substring(currentPosition));
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)`
    //        | number | functionName factor | factor `^` factor

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

        if (isAlphabetCharacter((char)currentCharacter))
            return parseFunctionFactor(this.currentPosition);

        throw new RuntimeException("Unexpected: " + (char) currentCharacter);
    }

    private boolean isNextParentheses() {
        return isNextOperation('(', '[', '{');
    }

    private Expression parseParenthesesFactor() {
        Expression x;
        char ending = getEndingParentheses();
        x = parseExpression();
        if (!eat(ending))
            throw new RuntimeException("No ending parenthesis: " + ending);
        return x;
    }

    private boolean isNumberCharacter(char currentCharacter) {
        return (currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.';
    }

    private Expression parseConstantFactor() {
        StringBuilder builder = new StringBuilder();
        while (isNumberCharacter((char) currentCharacter)){
            builder.append((char)currentCharacter);
            nextChar();
        }
        return ConstantExpression.of(Double.parseDouble(builder.toString()));
    }

    private boolean isAlphabetCharacter(char currentCharacter) {
        return isCharacterBetweenValues(currentCharacter, 'a', 'z');
    }

    private boolean isCharacterBetweenValues(char test, char left, char right){
        return test >= left && test <= right;
    }

    private Expression parseFunctionFactor(int startPos) {
        Expression x;
        while (isAlphabetCharacter((char)currentCharacter)) nextChar();
        String func = expression.substring(startPos, this.currentPosition);
        x = parseFactor();
        if (func.equals("sqrt")) x = SquareExpression.of(x);
        else if (func.equals("root")) x = SquareRootExpression.of(x);
        else throw new RuntimeException("Unknown function: " + func);
        return x;
    }

    private char getEndingParentheses() {
        switch (lastOperationCharacter) {
            case '(':
                return ')';
            case '[':
                return ']';
            case '{':
                return '}';
            default:
                throw new RuntimeException("Unknown ending parentheses: " + lastOperationCharacter);
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

    private void skipCharacters(IntPredicate predicate){
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
