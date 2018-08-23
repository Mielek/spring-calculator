package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionTokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

class BinaryExpressionFactory extends ExpressionFactoryChain {
    private Map<String, BiFunction<Expression, Expression, Expression>> operatorToCreator = new HashMap<>();

    void addBinaryFunctionCreator(String operation, BiFunction<Expression, Expression, Expression> creator) {
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

    private BiFunction<Expression, Expression, Expression> getCreator(String operator){
        BiFunction<Expression, Expression, Expression> creator = operatorToCreator.get(operator);
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return creator;
    }
}
