package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;
import com.mielowski.calculator.ExpressionTokenizer;

import java.util.function.Supplier;

public class FunctionExpressionFactory {

    private Supplier<Expression> childExpressionSupplier;

    public static FunctionExpressionFactory create() {
        return new FunctionExpressionFactory();
    }

    public FunctionExpressionFactory setChildExpressionSupplier(Supplier<Expression> childExpressionSupplier) {
        this.childExpressionSupplier = childExpressionSupplier;
        return this;
    }

    public Expression build(ExpressionTokenizer tokenizer){
        String function = tokenizer.getFunction();
        switch (function){
            case "sqrt":
                return SquareExpression.of(childExpressionSupplier.get());
            case "root":
                return SquareRootExpression.of(childExpressionSupplier.get());
            default:
                throw new ExpressionFactoryException("Unknown function: " + function);
        }
    }



}
