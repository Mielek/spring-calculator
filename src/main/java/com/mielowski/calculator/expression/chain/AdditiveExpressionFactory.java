package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.expression.binary.AdditionExpression;
import com.mielowski.calculator.expression.binary.SubtractionExpression;

class AdditiveExpressionFactory extends BinaryExpressionFactory {
    AdditiveExpressionFactory() {
        addBinaryFunctionCreator(AdditionExpression.OPERATOR, AdditionExpression::new);
        addBinaryFunctionCreator(SubtractionExpression.OPERATOR, SubtractionExpression::new);
    }
}
