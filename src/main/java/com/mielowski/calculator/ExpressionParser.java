package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

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

    private Expression createSubExpression(Supplier<Expression> nextParser, int... allowedOperations){
        Expression left = nextParser.get();
        while(isNextOperation(allowedOperations)){
            left = createBinaryExpression(lastOperationCharacter, left, nextParser.get());
        }
        return left;
    }

    private Expression parseFactor() {
        if(isUnaryOperator('+', '-'))
            return createUnaryExpression(lastOperationCharacter, parseFactor());

        if(isNextParentheses()) {
            return parseParenthesesFactor();
        } else if (isNumberCharacter()) { // numbers
            return parseConstantFactor(this.currentPosition);
        } else if (isAlphabetCharacter()) { // functions
            return parseFunctionFactor(this.currentPosition);
        } else {
            throw new RuntimeException("Unexpected: " + (char) currentCharacter);
        }
    }

    private boolean isNextParentheses() {
        return isNextOperation('(', '[', '{');
    }

    private Expression parseParenthesesFactor() {
        Expression x;
        char ending = getEndingParentheses();
        x = parseExpression();
        if (!eat(ending))
            throw new RuntimeException("No ending parenthesis: "+ending);
        return x;
    }

    private Expression parseFunctionFactor(int startPos) {
        Expression x;
        while (isAlphabetCharacter()) nextChar();
        String func = expression.substring(startPos, this.currentPosition);
        x = parseFactor();
        if (func.equals("sqrt")) x = SquareExpression.of(x);
        else if (func.equals("root")) x = SquareRootExpression.of(x);
        else throw new RuntimeException("Unknown function: " + func);
        return x;
    }

    private Expression parseConstantFactor(int startPos) {
        Expression x;
        while (isNumberCharacter()) nextChar();
        x = ConstantExpression.of(Double.parseDouble(expression.substring(startPos, this.currentPosition)));
        return x;
    }

    private char getEndingParentheses() {
        switch (lastOperationCharacter){
            case '(': return ')';
            case '[': return ']';
            case '{': return '}';
            default:
                throw new RuntimeException("Unknown ending parentheses: " + lastOperationCharacter);
        }
    }

    private boolean isAlphabetCharacter() {
        return currentCharacter >= 'a' && currentCharacter <= 'z';
    }

    private boolean isNumberCharacter() {
        return (currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.';
    }

    private boolean isUnaryOperator(int... operations) {
        return isNextOperation(operations);
    }

    private Expression createUnaryExpression(int unaryOperator, Expression unary) {
        switch (unaryOperator){
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
        while (currentCharacter == ' ') nextChar();
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
