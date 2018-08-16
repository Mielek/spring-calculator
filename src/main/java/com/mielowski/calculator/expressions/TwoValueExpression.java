package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

public abstract class TwoValueExpression implements Expression {

    protected Expression left;
    protected Expression right;

    public TwoValueExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
