package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class NegativeExpression extends OneValueExpression {

    public static NegativeExpression of(Expression child){
        return new NegativeExpression(child);
    }

    public NegativeExpression(Expression child) {
        super(child);
    }

    @Override
    public BigDecimal result() {
        return child.result().negate();
    }
}
