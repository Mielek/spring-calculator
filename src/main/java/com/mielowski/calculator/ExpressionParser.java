package com.mielowski.calculator;


import com.mielowski.calculator.expressions.*;

import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Based on <a href="https://stackoverflow.com/a/26227947">Thread on StackOverflow</a>
 */
public class ExpressionParser {

    private BinaryExpressionFactory binaryFactory = BinaryExpressionFactory.create();
    private UnaryExpressionFactory unaryFactory = UnaryExpressionFactory.create();

    private Tokenizer tokenizer;

    private String expression;
    private int currentCharacter;
    private int lastOperationCharacter;

    private Expression result;

    public ExpressionParser(String expression) {
        this.expression = prepare(expression);
        tokenizer = new Tokenizer(this.expression);
        throwIfExpressionIsEmpty();
        nextChar();
        result = parseExpression();
        throwIfUnknownEnding();
    }

    private String prepare(String expression) {
        return expression.trim().replace(" ", "").toLowerCase();
    }

    public Expression parse() {
        return result;
    }

    private void throwIfExpressionIsEmpty() {
        if (expression.isEmpty())
            throw new RuntimeException("Expression is empty");
    }

    private void throwIfUnknownEnding() {
        if (tokenizer.tmp())
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
        while (isNextOperation(allowedOperations)) {
            left = createBinaryExpression(lastOperationCharacter, left, nextParser.get());
        }
        return left;
    }

    private Expression parseFactor() {
        if (isUnaryOperation())
            return createUnaryExpression(lastOperationCharacter, parseFactor());

        if (isNextParentheses())
            return parseParenthesesFactor();

        if (isNumberCharacter((char) currentCharacter))
            return parseConstantFactor();

        if (isAlphabetCharacter((char) currentCharacter))
            return parseFunctionFactor();

        throw new RuntimeException("Unexpected: " + (char) currentCharacter);
    }

    private boolean isUnaryOperation() {
        return isNextOperation(UnaryExpressionFactory.getUnaryOperators());
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
        return unaryFactory.setChild(unary).build((char) unaryOperator);
    }

    private Expression createBinaryExpression(int binaryOperator, Expression left, Expression right) {
        return binaryFactory.setLeftExpression(left).setRightExpression(right).build((char) binaryOperator);
    }

    private boolean isNextOperation(int... operations) {
        if (IntStream.of(operations).anyMatch(value -> value == currentCharacter)) {
            lastOperationCharacter = currentCharacter;
            nextChar();
            return true;
        }
        return false;
    }

    private int nextChar() {
        return currentCharacter = tokenizer.nextToken();
    }

    private boolean eat(int charToEat) {
        if (currentCharacter == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    class Tokenizer {
        private String expression;
        private int currentPosition = -1;

        public Tokenizer(String expression) {
            this.expression = expression;
        }

        public boolean tmp(){
            return currentPosition < expression.length();
        }

        public int nextToken(){
            return (++currentPosition < expression.length()) ? expression.charAt(currentPosition) : -1;
        }

        public String getUnconsumedString() {
            return expression.substring(currentPosition);
        }
    }
}
