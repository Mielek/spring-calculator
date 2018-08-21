package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.unary.SquareExpression;
import com.mielowski.calculator.expression.unary.SquareRootExpression;

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
            case "sqr":
                return new SquareExpression(childExpressionSupplier.get());
            case "root":
                return new SquareRootExpression(childExpressionSupplier.get());
            default:
                throw new ExpressionFactoryException("Unknown function: " + function);
        }
    }



}
