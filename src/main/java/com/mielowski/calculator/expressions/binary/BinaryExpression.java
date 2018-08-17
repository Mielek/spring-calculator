package com.mielowski.calculator.expressions.binary;

import com.mielowski.calculator.Expression;

public abstract class BinaryExpression implements Expression {

    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}