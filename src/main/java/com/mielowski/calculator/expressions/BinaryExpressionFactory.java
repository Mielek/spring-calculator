package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

import java.util.function.Supplier;

public class BinaryExpressionFactory {

    private Supplier<Expression> leftS;
    private Supplier<Expression> rightS;

    public static BinaryExpressionFactory create(){
        return new BinaryExpressionFactory();
    }

    public BinaryExpressionFactory setLeftExpression(Expression left){
        this.leftS = () -> left;
        return this;
    }

    public BinaryExpressionFactory setRightExpression(Expression right){
        this.rightS = () -> right;
        return this;
    }

    public BinaryExpressionFactory setLeftExpressionSupplier(Supplier<Expression> leftS) {
        this.leftS = leftS;
        return this;
    }

    public BinaryExpressionFactory setRightExpressionSupplier(Supplier<Expression> rightS) {
        this.rightS = rightS;
        return this;
    }

    public Expression build(char operator){
        switch (operator) {
            case '+':
                return AdditionExpression.of(leftS.get(), rightS.get());
            case '-':
                return SubtractionExpression.of(leftS.get(), rightS.get());
            case '*':
                return MultiplyExpression.of(leftS.get(), rightS.get());
            case '/':
                return DivisionExpression.of(leftS.get(), rightS.get());
            default:
                throw new ExpressionFactoryException("Unknown operation under char: " + operator);
        }
    }

    public static int[] getAdditiveOperators(){
        return new int[] {'+','-'};
    }

    public static int[] getMultiplicationOperators(){
        return new int[] {'*','/'};
    }

}
