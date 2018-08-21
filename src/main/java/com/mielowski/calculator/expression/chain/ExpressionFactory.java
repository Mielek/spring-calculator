package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;

public interface ExpressionFactory {
    Expression create(ExpressionTokenizer tokenizer);
}
