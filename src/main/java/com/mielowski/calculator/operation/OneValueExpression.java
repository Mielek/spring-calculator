package com.mielowski.calculator.operation;

public abstract class OneValueExpression implements Expression {
    protected Expression child;

    public OneValueExpression(Expression child) {
        this.child = child;
    }
}
