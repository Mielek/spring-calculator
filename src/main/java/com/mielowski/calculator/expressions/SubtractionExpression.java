package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class SubtractionExpression extends BinaryExpression {

    public SubtractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    public static Expression of(Expression left, Expression right) {
        return new SubtractionExpression(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().subtract(right.result());
    }

    @Override
    public String toString() {
        return left.toString()+"-"+right.toString();
    }
}
