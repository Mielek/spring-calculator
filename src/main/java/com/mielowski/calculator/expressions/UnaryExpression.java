package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

public abstract class UnaryExpression implements Expression {
    protected Expression child;

    public UnaryExpression(Expression child) {
        this.child = child;
    }
}
