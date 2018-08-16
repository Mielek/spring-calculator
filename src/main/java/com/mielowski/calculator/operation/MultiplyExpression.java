package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class MultiplyExpression extends TwoValueExpression {
    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().multiply(right.result());
    }
}
