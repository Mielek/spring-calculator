package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.binary.AdditionExpression;
import com.mielowski.calculator.expression.binary.DivisionExpression;
import com.mielowski.calculator.expression.binary.MultiplyExpression;
import com.mielowski.calculator.expression.binary.SubtractionExpression;

import java.util.Map;
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

    public Expression build(ExpressionTokenizer tokenizer){
        char operator = tokenizer.returnLastAndMove();
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
