package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;

public class BinaryExpressionFactory {

    private Expression left;
    private Expression right;

    public static BinaryExpressionFactory create(){
        return new BinaryExpressionFactory();
    }

    public BinaryExpressionFactory setLeftExpression(Expression left){
        this.left = left;
        return this;
    }

    public BinaryExpressionFactory setRightExpression(Expression right){
        this.right = right;
        return this;
    }

    public Expression build(char operator){
        switch (operator) {
            case '+':
                return AdditionExpression.of(left, right);
            case '-':
                return SubtractionExpression.of(left, right);
            case '*':
                return MultiplyExpression.of(left, right);
            case '/':
                return DivisionExpression.of(left, right);
            default:
                throw new RuntimeException("Unknown operation under char: " + operator);
        }
    }

    public static int[] getAdditiveOperators(){
        return new int[] {'+','-'};
    }

    public static int[] getMultiplicationOperators(){
        return new int[] {'*','/'};
    }

}
