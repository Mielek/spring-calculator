package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class ConstantExpression implements Expression {

    private BigDecimal constant;

    public static ConstantExpression of(BigDecimal value){
        return new ConstantExpression(value);
    }

    public static ConstantExpression of(Double value){
        return new ConstantExpression(BigDecimal.valueOf(value));
    }

    private ConstantExpression(BigDecimal constant) {
        this.constant = constant;
    }

    @Override
    public BigDecimal result() {
        return constant;
    }
}
