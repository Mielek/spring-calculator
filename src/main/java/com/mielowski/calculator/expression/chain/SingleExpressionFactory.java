package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.unary.NegativeExpression;

import java.util.function.Function;

public class SingleExpressionFactory extends UnaryExpressionFactory {

    public SingleExpressionFactory() {
        addUnaryFunctionCreator("+", Function.identity());
        addUnaryFunctionCreator(String.valueOf(NegativeExpression.OPERATOR), NegativeExpression::new);
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        char operator = tokenizer.getCurrentToken();
        if(hasRegisteredOperator(String.valueOf(operator)))
            return getCreator(String.valueOf(tokenizer.getCurrentAndMove())).apply(nextInChain.create(tokenizer));
        return nextInChain.create(tokenizer);
    }

}
