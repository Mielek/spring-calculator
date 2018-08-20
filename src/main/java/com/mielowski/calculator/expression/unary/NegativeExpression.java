package com.mielowski.calculator.expression.unary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class NegativeExpression extends UnaryExpression {

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

    @Override
    public String toString() {
        return "-"+child.toString();
    }
}
