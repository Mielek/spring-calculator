package com.mielowski.calculator.expression.binary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class DivisionExpression extends BinaryExpression {
    public static final Character OPERATOR = '/';

    public DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().divide(right.result());
    }

    @Override
    public String toString() {
        return left.toString()+"/"+right.toString();
    }
}
