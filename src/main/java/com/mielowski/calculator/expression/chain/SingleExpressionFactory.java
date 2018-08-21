package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.unary.NegativeExpression;

import java.util.function.Function;

public class SingleExpressionFactory extends UnaryExpressionFactory {

    public SingleExpressionFactory() {
        addUnaryFunctionCreator("+", Function.identity());
        addUnaryFunctionCreator(NegativeExpression.OPERATOR, NegativeExpression::new);
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        ExpressionTokenizer.Token token = tokenizer.getToken();
        if(token.isOperation() && hasRegisteredOperator(token.getValue())) {
            return getCreator(tokenizer.getTokenAndMove().getValue()).apply(nextInChain.create(tokenizer));
        }
        return nextInChain.create(tokenizer);
    }

}
