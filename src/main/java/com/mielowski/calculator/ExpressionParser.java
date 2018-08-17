package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private BinaryExpressionFactory additiveFactory = BinaryExpressionFactory.create().setRightExpressionSupplier(this::parseTerm);
    private BinaryExpressionFactory multiplicativeFactory = BinaryExpressionFactory.create().setRightExpressionSupplier(this::parseFactor);
    private UnaryExpressionFactory unaryFactory = UnaryExpressionFactory.create().setChildExpressionSupplier(this::parseFactor);
    private FunctionExpressionFactory functionFactory = FunctionExpressionFactory.create().setChildExpressionSupplier(this::parseFactor);
    private ParenthesisExpressionFactory parenthesisExpressionFactory = ParenthesisExpressionFactory.create().setSubExpressionSuplier(this::parseExpression);

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
        Expression left = parseTerm();
        while (isAdditiveOperation()) {
            left = additiveFactory.setLeftExpression(left).build((char) tokenizer.returnLastAndMove());
        }
        return left;
    }

    private boolean isAdditiveOperation() {
        return tokenizer.isCurrentAnOperation(BinaryExpressionFactory.getAdditiveOperators());
    }

    private Expression parseTerm() {
        Expression left = parseFactor();
        while (isMultiplicationOperation()) {
            left = multiplicativeFactory.setLeftExpression(left).build((char) tokenizer.returnLastAndMove());
        }
        return left;
    }

    private boolean isMultiplicationOperation() {
        return tokenizer.isCurrentAnOperation(BinaryExpressionFactory.getMultiplicationOperators());
    }

    private Expression parseFactor() {
        if (isUnaryOperation())
            return createUnaryExpression();

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
        return tokenizer.isCurrentAnOperation(ParenthesisExpressionFactory.getStartingParentheses());
    }

    private Expression parseParenthesesFactor() {
        return parenthesisExpressionFactory.build(tokenizer);
    }

    private Expression parseConstantFactor() {
        String number = tokenizer.getValue();
        return ConstantExpression.of(Double.parseDouble(number));
    }

    private Expression parseFunctionFactor() {
        return functionFactory.build(tokenizer);
    }

    private Expression createUnaryExpression() {
        return unaryFactory.build(tokenizer);
    }
}
