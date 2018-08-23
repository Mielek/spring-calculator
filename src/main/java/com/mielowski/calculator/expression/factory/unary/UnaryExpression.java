package com.mielowski.calculator.expression.factory.unary;

import com.mielowski.calculator.core.Expression;

public abstract class UnaryExpression implements Expression {
    protected Expression child;

    public UnaryExpression(Expression child) {
        this.child = child;
    }
}
