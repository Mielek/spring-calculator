package com.mielowski.calculator.expression.factory;

import com.mielowski.calculator.expression.factory.binary.AdditionExpression;
import com.mielowski.calculator.expression.factory.binary.SubtractionExpression;

class AdditiveExpressionFactory extends BinaryExpressionFactory {
    AdditiveExpressionFactory() {
        addBinaryFunctionCreator(AdditionExpression.OPERATOR, AdditionExpression::new);
        addBinaryFunctionCreator(SubtractionExpression.OPERATOR, SubtractionExpression::new);
    }
}
