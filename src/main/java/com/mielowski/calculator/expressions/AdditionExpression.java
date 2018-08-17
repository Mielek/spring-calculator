package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.math.BigDecimal;

public class AdditionExpression extends BinaryExpression {

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

    @Override
    public String toString() {
        return left.toString()+"+"+right.toString();
    }
}
