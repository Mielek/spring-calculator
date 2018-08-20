package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.unary.NegativeExpression;

import java.util.function.Supplier;

public class UnaryExpressionFactory {

    private Supplier<Expression> childExpressionSupplier;

    public static UnaryExpressionFactory create() {
        return new UnaryExpressionFactory();
    }

    public static int[] getUnaryOperators() {
        return new int[] {'+', '-'};
    }


    public UnaryExpressionFactory setChildExpressionSupplier(Supplier<Expression> childExpressionSupplier) {
        this.childExpressionSupplier = childExpressionSupplier;
        return this;
    }

    public Expression build(ExpressionTokenizer tokenizer){
        char operator = (char) tokenizer.returnLastAndMove();
        switch (operator) {
            case '+':
                return childExpressionSupplier.get();
            case '-':
                return NegativeExpression.of(childExpressionSupplier.get());
            default:
                throw new ExpressionFactoryException("Unknown unary operation under char: " + operator);
        }
    }
}
