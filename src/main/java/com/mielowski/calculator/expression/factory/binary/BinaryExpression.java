package com.mielowski.calculator.expression.factory.binary;

import com.mielowski.calculator.core.Expression;

public abstract class BinaryExpression implements Expression {

    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
