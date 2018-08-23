package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;

import java.math.BigDecimal;

class ValueExpressionFactory extends ExpressionFactoryChain {
    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        ExpressionTokenizer.Token token = tokenizer.getToken();
        if(token.isValue()) {
            tokenizer.nextToken();
            return new ValueExpression(BigDecimal.valueOf(Double.parseDouble(token.getValue())));
        }
        return nextInChain.create(tokenizer);
    }
}
