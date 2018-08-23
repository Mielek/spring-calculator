package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;

import java.util.HashMap;
import java.util.Map;

class BinaryExpressionFactory extends ExpressionFactoryChain {
    private Map<String, BinaryExpressionCreator> operatorToCreator = new HashMap<>();

    void addBinaryFunctionCreator(String operation, BinaryExpressionCreator creator) {
        operatorToCreator.put(String.valueOf(operation), creator);
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        Expression left = nextInChain.create(tokenizer);
        while (isOperationRegistered(tokenizer)) {
            ExpressionTokenizer.Token operator = tokenizer.getTokenAndMove();
            left = getCreator(operator.getValue()).apply(left, nextInChain.create(tokenizer));
        }
        return left;
    }

    private boolean isOperationRegistered(ExpressionTokenizer tokenizer) {
        return operatorToCreator.keySet().contains(tokenizer.getToken().getValue());
    }

    private BinaryExpressionCreator getCreator(String operator) {
        BinaryExpressionCreator creator = operatorToCreator.get(operator);
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return creator;
    }
}
