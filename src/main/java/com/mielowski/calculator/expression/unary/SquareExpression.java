package com.mielowski.calculator.expression.unary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class SquareExpression extends UnaryExpression {
    public static final String OPERATOR = "sqr";

    public SquareExpression(Expression child) {
        super(child);
    }

    @Override
    public BigDecimal result() {
        return child.result().pow(2);
    }

    @Override
    public String toString() {
        return "sqr"+child.toString();
    }
}
