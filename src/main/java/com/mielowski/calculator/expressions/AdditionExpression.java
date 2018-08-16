package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class AdditionExpression extends TwoValueExpression {

    public static AdditionExpression of(Expression left, Expression right){
        return new AdditionExpression(left, right);
    }

    AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public BigDecimal result() {
        return left.result().add(right.result());
    }
}
