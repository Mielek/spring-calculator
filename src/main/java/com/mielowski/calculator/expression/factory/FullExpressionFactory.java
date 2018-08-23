package com.mielowski.calculator.expression.factory;

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
            throw new ExpressionParserException("Unexpected character: " + t.getToken());
        };

        additive.setNextFactory(multiplicative);
        multiplicative.setNextFactory(single);
        single.setNextFactory(parenthesis);
        parenthesis.setNextFactory(value);
        value.setNextFactory(function);
        function.setNextFactory(error);

        parenthesis.setInnerExpressionFactory(additive);
        function.setInnerExpressionFactory(single);

        chainRoot = additive;
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        return chainRoot.create(tokenizer);
    }
}
