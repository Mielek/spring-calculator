package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class ValueOperation implements Operation {

    private BigDecimal value;

    public static ValueOperation of(BigDecimal value){
        return new ValueOperation(value);
    }
    public static ValueOperation of(Double value){
        return new ValueOperation(BigDecimal.valueOf(value));
    }

    public ValueOperation(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal result() {
        return value;
    }
}
