package com.mielowski.calculator.expression.binary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class AdditionExpression extends BinaryExpression {
    public static final Character OPERATOR = '+';

    public AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().add(right.result());
    }

    @Override
    public String toString() {
        return left.toString()+"+"+right.toString();
    }
}
