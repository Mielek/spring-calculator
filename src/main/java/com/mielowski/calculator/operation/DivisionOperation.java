package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class DivisionOperation extends TwoValueOperation {
    public DivisionOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().divide(right.result());
    }
}
