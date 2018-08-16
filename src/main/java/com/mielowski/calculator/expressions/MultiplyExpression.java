package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class MultiplyExpression extends TwoValueExpression {

    public static MultiplyExpression of(Expression left, Expression right){
        return new MultiplyExpression(left, right);
    }

    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().multiply(right.result());
    }
}
