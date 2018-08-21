package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class ValueExpression implements Expression {

    private BigDecimal constant;

    public ValueExpression(BigDecimal constant) {
        this.constant = constant;
    }

    @Override
    public BigDecimal result() {
        return constant;
    }

    @Override
    public String toString() {
        return constant.toPlainString();
    }
}
