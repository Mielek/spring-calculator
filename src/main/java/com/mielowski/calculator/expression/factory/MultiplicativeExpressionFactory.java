package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.expression.factory.binary.DivisionExpression;
import com.mielowski.calculator.expression.factory.binary.MultiplyExpression;

class MultiplicativeExpressionFactory extends BinaryExpressionFactory {
    MultiplicativeExpressionFactory() {
        addBinaryFunctionCreator(MultiplyExpression.OPERATOR, MultiplyExpression::new);
        addBinaryFunctionCreator(DivisionExpression.OPERATOR, DivisionExpression::new);
    }
}
