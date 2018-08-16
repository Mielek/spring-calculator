package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class MultiplyOperation extends TwoValueOperation {
    public MultiplyOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().multiply(right.result());
    }
}
