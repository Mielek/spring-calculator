package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.factory.unary.NegativeExpression;

class SingleExpressionFactory extends UnaryExpressionFactory {

    SingleExpressionFactory() {
        addUnaryFunctionCreator("+", UnaryExpressionCreator.identity());
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
