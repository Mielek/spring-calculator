package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class SubtractionExpression extends TwoValueExpression {

    public SubtractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().subtract(right.result());
    }
}
