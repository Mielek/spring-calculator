package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionTokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UnaryExpressionFactory extends ExpressionFactoryChain {
    private Map<String, Function<Expression, Expression>> operatorToCreator = new HashMap<>();

    public UnaryExpressionFactory(ExpressionFactoryChain nextInChain) {
        super(nextInChain);
    }
    public void addUnaryFunctionCreator(String operation, Function<Expression, Expression> creator){
        operatorToCreator.put(operation, creator);
    }

    @Override
    public Expression parse(ExpressionTokenizer tokenizer) {
        char operator = tokenizer.getCurrentAndMove();
        return getCreator(operator).apply(nextInChain.parse(tokenizer));
    }

    private Function<Expression, Expression> getCreator(char operator) {
        Function<Expression, Expression> creator = operatorToCreator.get(String.valueOf(operator));
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return null;
    }
}
