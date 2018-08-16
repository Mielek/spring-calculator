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
            char lastOperation = (char) lastOperationCharacter;
            left = createTwoValueExpression(lastOperation, left, nextParser.get());
        }
        return left;
    }

    private Expression parseFactor() {
        if (eat('+')) return parseFactor(); // unary plus
        if (eat('-')) return NegativeExpression.of(parseFactor()); // unary minus

        Expression x;
        int startPos = this.currentPosition;
        if (eat('(')) { // parentheses
            x = parseExpression();
            if (!eat(')'))
                throw new RuntimeException("No ending parenthesis");
        } else if (eat('[')) { // parentheses
            x = parseExpression();
            if (!eat(']'))
                throw new RuntimeException("No ending parenthesis");
        } else if (eat('{')) { // parentheses
            x = parseExpression();
            if (!eat('}'))
                throw new RuntimeException("No ending parenthesis");
        } else if ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') { // numbers
            while ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') nextChar();
            x = ConstantExpression.of(Double.parseDouble(expression.substring(startPos, this.currentPosition)));
        } else if (currentCharacter >= 'a' && currentCharacter <= 'z') { // functions
            while (currentCharacter >= 'a' && currentCharacter <= 'z') nextChar();
            String func = expression.substring(startPos, this.currentPosition);
            x = parseFactor();
            if (func.equals("sqrt")) x = SquareExpression.of(x);
            else if (func.equals("root")) x = SquareRootExpression.of(x);
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) currentCharacter);
        }

        return x;
    }

    private Expression createTwoValueExpression(char tmp, Expression left, Expression right) {
        switch (tmp) {
            case '+':
                return AdditionExpression.of(left, right);
            case '-':
                return SubtractionExpression.of(left, right);
            case '*':
                return MultiplyExpression.of(left, right);
            case '/':
                return DivisionExpression.of(left, right);
            default:
                throw new RuntimeException("Unknown operation under char: " + tmp);
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
