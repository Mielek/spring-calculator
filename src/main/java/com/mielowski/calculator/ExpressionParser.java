package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private String expression;
    private int currentPosition = -1;
    private int currentCharacter;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }


    public Expression parse() {
        nextChar();
        Expression x = parseExpression();
        if (currentPosition < expression.length())
            throw new RuntimeException("Unexpected: " + (char) currentCharacter);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)`
    //        | number | functionName factor | factor `^` factor

    private Expression parseExpression() {
        Expression x = parseTerm();
        for (;;) {
            if      (eat('+')) x = AdditionExpression.of(x, parseTerm()); // addition
            else if (eat('-')) x = SubtractionExpression.of(x, parseTerm()); // subtraction
            else return x;
        }
    }

    private Expression parseTerm() {
        Expression x = parseFactor();
        for (;;) {
            if      (eat('*')) x = MultiplyExpression.of(x, parseFactor()); // multiplication
            else if (eat('/')) x = DivisionExpression.of(x, parseFactor()); // division
            else return x;
        }
    }

    private Expression parseFactor() {
        if (eat('+')) return parseFactor(); // unary plus
        if (eat('-')) return NegativeExpression.of(parseFactor()); // unary minus

        Expression x;
        int startPos = this.currentPosition;
        if (eat('(')) { // parentheses
            x = parseExpression();
            eat(')');
        } else if (eat('[')) { // parentheses
            x = parseExpression();
            eat(']');
        } else if (eat('{')) { // parentheses
            x = parseExpression();
            eat('}');
        } else if ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') { // numbers
            while ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') nextChar();
            x = ConstantExpression.of(Double.parseDouble(expression.substring(startPos, this.currentPosition)));
        } else if (currentCharacter >= 'a' && currentCharacter <= 'z') { // functions
            while (currentCharacter >= 'a' && currentCharacter <= 'z') nextChar();
            String func = expression.substring(startPos, this.currentPosition);
            x = parseFactor();
            if (func.equals("sqrt")) x = SquareExpression.of(x);
            else if(func.equals("root")) x = SquareRootExpression.of(x);
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) currentCharacter);
        }

        return x;
    }

    private void nextChar() {
        currentCharacter = (++currentPosition < expression.length()) ? expression.charAt(currentPosition) : -1;
    }

    private boolean eat(int charToEat) {
        while (currentCharacter == ' ') nextChar();
        if (currentCharacter == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }
}
