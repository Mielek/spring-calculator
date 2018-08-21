package com.mielowski.calculator.expression.binary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class SubtractionExpression extends BinaryExpression {
    public static final String OPERATOR = "-";

    public SubtractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().subtract(right.result());
    }

    @Override
    public String toString() {
        return left.toString()+"-"+right.toString();
    }
}
