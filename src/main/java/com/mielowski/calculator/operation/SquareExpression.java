package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class SquareExpression extends OneValueExpression {

    public SquareExpression(Expression child) {
        super(child);
    }

    @Override
    public BigDecimal result() {
        return child.result().pow(2);
    }
}
