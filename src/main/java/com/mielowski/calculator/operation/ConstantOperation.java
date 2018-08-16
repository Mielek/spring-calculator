package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class ConstantOperation implements Operation {

    private BigDecimal constant;

    public static ConstantOperation of(BigDecimal value){
        return new ConstantOperation(value);
    }

    public static ConstantOperation of(Double value){
        return new ConstantOperation(BigDecimal.valueOf(value));
    }

    private ConstantOperation(BigDecimal constant) {
        this.constant = constant;
    }

    @Override
    public BigDecimal result() {
        return constant;
    }
}
