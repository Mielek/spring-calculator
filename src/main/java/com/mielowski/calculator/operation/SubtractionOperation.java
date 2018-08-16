package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class SubtractionOperation extends TwoValueOperation {

    public SubtractionOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().subtract(right.result());
    }
}
