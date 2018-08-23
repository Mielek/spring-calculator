package com.mielowski.calculator.expression.factory;

import java.util.HashMap;
import java.util.Map;

abstract class UnaryExpressionFactory extends ExpressionFactoryChain {
    private Map<String, UnaryExpressionCreator> operatorToCreator = new HashMap<>();

    void addUnaryFunctionCreator(String operation, UnaryExpressionCreator creator) {
        operatorToCreator.put(operation, creator);
    }

    boolean hasRegisteredOperator(String operator) {
        return operatorToCreator.containsKey(operator);
    }

    UnaryExpressionCreator getCreator(String operator) {
        UnaryExpressionCreator creator = operatorToCreator.get(operator);
        if(creator==null)
            throw new ExpressionFactoryException(String.format("Unknown operation %1s , registered are %2s", operator, operatorToCreator.keySet().toString()));
        return creator;
    }
}
