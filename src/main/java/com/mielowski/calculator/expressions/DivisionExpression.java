package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class DivisionExpression extends TwoValueExpression {

    public static DivisionExpression of(Expression left, Expression right){
        return new DivisionExpression(left, right);
    }

    public DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().divide(right.result());
    }
}