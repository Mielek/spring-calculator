package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

public class UnaryExpressionFactory {

    private Expression child;

    public static UnaryExpressionFactory create() {
        return new UnaryExpressionFactory();
    }

    public static int[] getUnaryOperators() {
        return new int[] {'+', '-'};
    }

    public UnaryExpressionFactory setChild(Expression child) {
        this.child = child;
        return this;
    }

    public Expression build(char operator){
        switch (operator) {
            case '+':
                return child;
            case '-':
                return NegativeExpression.of(child);
            default:
                throw new RuntimeException("Unknown unary operation under char: " + operator);
        }
    }
}
