package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionParserException;
import com.mielowski.calculator.expression.ExpressionTokenizer;

public class FullExpressionFactory implements ExpressionFactory {
    private ExpressionFactory chainRoot;

    public FullExpressionFactory() {
        AdditiveExpressionFactory additive = new AdditiveExpressionFactory();
        MultiplicativeExpressionFactory multiplicative = new MultiplicativeExpressionFactory();
        SingleExpressionFactory single = new SingleExpressionFactory();
        FunctionExpressionFactory function = new FunctionExpressionFactory();
        ParenthesisExpressionFactory parenthesis = new ParenthesisExpressionFactory();
        ValueExpressionFactory value = new ValueExpressionFactory();
        ExpressionFactory error = t -> {
            throw new ExpressionParserException("Unexpected character: " + t.getCurrentToken());
        };

        additive.setNextInChain(multiplicative);
        multiplicative.setNextInChain(single);
        single.setNextInChain(parenthesis);
        parenthesis.setNextInChain(value);
        value.setNextInChain(function);
        function.setNextInChain(error);

        parenthesis.setInnerExpression(additive);
        function.setInnerExpression(single);

        chainRoot = additive;
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        return chainRoot.create(tokenizer);
    }
}
