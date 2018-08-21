package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionTokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class BinaryExpressionFactory extends ExpressionFactoryChain {
    private Map<Character, BiFunction<Expression, Expression, Expression>> operatorToCreator = new HashMap<>();

    public void addBinaryFunctionCreator(Character operation, BiFunction<Expression, Expression, Expression> creator){
        operatorToCreator.put(operation, creator);
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        Expression left = nextInChain.create(tokenizer);
        while (isOperationRegistered(tokenizer)) {
            char operator = tokenizer.getCurrentAndMove();
            left = getCreator(operator).apply(left, nextInChain.create(tokenizer));
        }
        return left;
    }

    private boolean isOperationRegistered(ExpressionTokenizer tokenizer) {
        return operatorToCreator.keySet().contains(tokenizer.getCurrentToken());
    }

    private BiFunction<Expression, Expression, Expression> getCreator(Character operator){
        BiFunction<Expression, Expression, Expression> creator = operatorToCreator.get(operator);
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return creator;
    }
}
