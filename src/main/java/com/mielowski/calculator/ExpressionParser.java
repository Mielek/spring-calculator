package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private String expression;
    int pos = -1, ch;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    void nextChar() {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    Expression parse() {
        nextChar();
        Expression x = parseExpression();
        if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)`
    //        | number | functionName factor | factor `^` factor

    Expression parseExpression() {
        Expression x = parseTerm();
        for (;;) {
            if      (eat('+')) x = AdditionExpression.of(x, parseTerm()); // addition
            else if (eat('-')) x = SubtractionExpression.of(x, parseTerm()); // subtraction
            else return x;
        }
    }

    Expression parseTerm() {
        Expression x = parseFactor();
        for (;;) {
            if      (eat('*')) x = MultiplyExpression.of(x, parseFactor()); // multiplication
            else if (eat('/')) x = DivisionExpression.of(x, parseFactor()); // division
            else return x;
        }
    }

    Expression parseFactor() {
        if (eat('+')) return parseFactor(); // unary plus
        if (eat('-')) return NegativeExpression.of(parseFactor()); // unary minus

        Expression x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            eat(')');
        } else if (eat('[')) { // parentheses
            x = parseExpression();
            eat(']');
        } else if (eat('{')) { // parentheses
            x = parseExpression();
            eat('}');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = ConstantExpression.of(Double.parseDouble(expression.substring(startPos, this.pos)));
        } else if (ch >= 'a' && ch <= 'z') { // functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = expression.substring(startPos, this.pos);
            x = parseFactor();
            if (func.equals("sqrt")) x = SquareExpression.of(x);
            else if(func.equals("root")) x = SquareRootExpression.of(x);
            //else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
            //else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
            //else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char)ch);
        }

        //if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

        return x;
    }
}
