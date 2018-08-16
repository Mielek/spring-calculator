package com.mielowski.calculator.operation;

public abstract class TwoValueExpression implements Expression {

    protected Expression left;
    protected Expression right;

    public TwoValueExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
