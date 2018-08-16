package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class DivisionExpression extends TwoValueExpression {
    public DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().divide(right.result());
    }
}
