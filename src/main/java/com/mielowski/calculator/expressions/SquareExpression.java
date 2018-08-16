package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class SquareExpression extends OneValueExpression {

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
}
