package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class UnaryExpressionFactory extends ExpressionFactoryChain {
    private Map<String, Function<Expression, Expression>> operatorToCreator = new HashMap<>();

    public void addUnaryFunctionCreator(String operation, Function<Expression, Expression> creator){
        operatorToCreator.put(operation, creator);
    }

    protected boolean hasRegisteredOperator(String operator){
        return operatorToCreator.containsKey(operator);
    }

    protected Function<Expression, Expression> getCreator(String operator) {
        Function<Expression, Expression> creator = operatorToCreator.get(operator);
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return creator;
    }
}
