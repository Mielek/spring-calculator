package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class AdditionOperation extends TwoValueOperation {

    public AdditionOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().add(right.result());
    }
}
