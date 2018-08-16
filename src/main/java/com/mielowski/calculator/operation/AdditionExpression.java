package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class AdditionExpression extends TwoValueExpression {

    public AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().add(right.result());
    }
}
