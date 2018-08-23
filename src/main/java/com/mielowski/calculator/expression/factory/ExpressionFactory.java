package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;

public interface ExpressionFactory {
    Expression create(ExpressionTokenizer tokenizer);
}
