package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;

import java.util.function.BiFunction;

@FunctionalInterface
interface BinaryExpressionCreator extends BiFunction<Expression, Expression, Expression> {
}
