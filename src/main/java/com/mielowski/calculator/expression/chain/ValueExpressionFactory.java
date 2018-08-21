package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.ValueExpression;

import java.math.BigDecimal;

public class ValueExpressionFactory extends ExpressionFactoryChain {

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        if(tokenizer.isValueToken())
            return new ValueExpression(BigDecimal.valueOf(Double.parseDouble(tokenizer.getValue())));
        return nextInChain.create(tokenizer);
    }
}
