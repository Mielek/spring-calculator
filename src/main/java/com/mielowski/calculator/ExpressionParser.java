package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private BinaryExpressionFactory binaryFactory = BinaryExpressionFactory.create();
    private UnaryExpressionFactory unaryFactory = UnaryExpressionFactory.create();

    private ExpressionTokenizer tokenizer;
    private Expression result;

    public ExpressionParser(String expression) {
        tokenizer = new ExpressionTokenizer(expression);
        throwIfExpressionIsEmpty();
        result = parseExpression();
        throwIfUnknownEnding();
    }

    public Expression parse() {
        return result;
    }

    private void throwIfExpressionIsEmpty() {
        if (!tokenizer.hasNext())
            throw new RuntimeException("Expression is empty");
    }

    private void throwIfUnknownEnding() {
        if (tokenizer.hasNext())
            throw new RuntimeException("Unexpected expression ending " + tokenizer.getUnconsumedString());
    }

    private Expression parseExpression() {
        return createSubExpression(this::parseTerm, BinaryExpressionFactory.getAdditiveOperators());
    }

    private Expression parseTerm() {
        return createSubExpression(this::parseFactor, BinaryExpressionFactory.getMultiplicationOperators());
    }

    private Expression createSubExpression(Supplier<Expression> nextParser, int... allowedOperations) {
        Expression left = nextParser.get();
        while (tokenizer.isCurrentAnOperation(allowedOperations)) {
            left = createBinaryExpression(tokenizer.returnLastAndMove(), left, nextParser.get());
        }
        return left;
    }

    private Expression parseFactor() {
        if (isUnaryOperation())
            return createUnaryExpression(tokenizer.returnLastAndMove(), parseFactor());

        if (isNextParentheses())
            return parseParenthesesFactor();

        if (tokenizer.isValueToken())
            return parseConstantFactor();

        if (tokenizer.isFunctionToken())
            return parseFunctionFactor();

        throw new RuntimeException("Unexpected: " + (char) tokenizer.getCurrentToken());
    }

    private boolean isUnaryOperation() {
        return tokenizer.isCurrentAnOperation(UnaryExpressionFactory.getUnaryOperators());
    }

    private boolean isNextParentheses() {
        return tokenizer.isCurrentAnOperation('(', '[', '{');
    }

    private Expression parseParenthesesFactor() {
        char ending = getEndingParentheses(tokenizer.returnLastAndMove());
        Expression x = parseExpression();
        if (tokenizer.getCurrentToken() != ending)
            throw new RuntimeException("No ending parenthesis: " + ending);
        tokenizer.nextToken();
        return x;
    }


    private Expression parseConstantFactor() {
        String number = tokenizer.getValue();
        return ConstantExpression.of(Double.parseDouble(number));
    }

    private Expression parseFunctionFactor() {
        String func = tokenizer.getFunction();
        Expression x = parseFactor();
        if (func.equals("sqrt")) x = SquareExpression.of(x);
        else if (func.equals("root")) x = SquareRootExpression.of(x);
        else throw new RuntimeException("Unknown function: " + func);
        return x;
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
        return unaryFactory.setChild(unary).build((char) unaryOperator);
    }

    private Expression createBinaryExpression(int binaryOperator, Expression left, Expression right) {
        return binaryFactory.setLeftExpression(left).setRightExpression(right).build((char) binaryOperator);
    }
}
