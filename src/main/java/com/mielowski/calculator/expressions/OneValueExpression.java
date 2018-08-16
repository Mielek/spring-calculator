package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

public abstract class OneValueExpression implements Expression {
    protected Expression child;

    public OneValueExpression(Expression child) {
        this.child = child;
    }
}
