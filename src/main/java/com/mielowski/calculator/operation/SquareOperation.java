package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class SquareOperation implements Operation {

    Operation child;

    public SquareOperation(Operation child) {
        this.child = child;
    }

    @Override
    public BigDecimal result() {
        return child.result().pow(2);
    }
}
