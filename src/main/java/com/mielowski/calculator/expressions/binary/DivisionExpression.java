package com.mielowski.calculator.expressions.binary;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class DivisionExpression extends BinaryExpression {

    public static DivisionExpression of(Expression left, Expression right){
        return new DivisionExpression(left, right);
    }

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
