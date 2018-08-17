package com.mielowski.calculator.expressions.unary;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class SquareExpression extends UnaryExpression {

    public static Expression of(Expression child) {
        return new SquareExpression(child);
    }

    public SquareExpression(Expression child) {
        super(child);
    }


    @Override
    public BigDecimal result() {
        return child.result().pow(2);
    }

    @Override
    public String toString() {
        return "sqrt"+child.toString();
    }
}
