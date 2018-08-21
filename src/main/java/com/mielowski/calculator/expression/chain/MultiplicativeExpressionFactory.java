package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.expression.binary.DivisionExpression;
import com.mielowski.calculator.expression.binary.MultiplyExpression;

public class MultiplicativeExpressionFactory extends BinaryExpressionFactory {
    public MultiplicativeExpressionFactory(ExpressionFactoryChain nextInChain) {
        super(nextInChain);
        addBinaryFunctionCreator(MultiplyExpression.OPERATOR, MultiplyExpression::new);
        addBinaryFunctionCreator(DivisionExpression.OPERATOR, DivisionExpression::new);
    }
}