package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;

import java.util.function.Function;

@FunctionalInterface
interface UnaryExpressionCreator extends Function<Expression, Expression> {
    static UnaryExpressionCreator identity() {
        return t -> t;
    }
}
