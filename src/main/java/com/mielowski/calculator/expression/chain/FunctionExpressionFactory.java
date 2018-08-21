package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.unary.SquareExpression;
import com.mielowski.calculator.expression.unary.SquareRootExpression;

public class FunctionExpressionFactory extends UnaryExpressionFactory {

    private ExpressionFactory innerExpression;

    public FunctionExpressionFactory() {
        addUnaryFunctionCreator(SquareExpression.OPERATOR, SquareExpression::new);
        addUnaryFunctionCreator(SquareRootExpression.OPERATOR, SquareRootExpression::new);
    }

    public void setInnerExpression(ExpressionFactory innerExpression) {
        this.innerExpression = innerExpression;
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        if(tokenizer.isFunctionToken())
            return getCreator(tokenizer.getFunction()).apply(innerExpression.create(tokenizer));
        return nextInChain.create(tokenizer);
    }


}