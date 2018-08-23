package com.mielowski.calculator.expression.factory.unary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class ParenthesisExpression extends UnaryExpression {

    private String openParenthesis;
    private String closeParenthesis;


    public ParenthesisExpression(Expression child, String openParenthesis, String closeParenthesis) {
        super(child);
        this.openParenthesis = openParenthesis;
        this.closeParenthesis = closeParenthesis;
    }

    @Override
    public BigDecimal result() {
        return child.result();
    }

    @Override
    public String toString() {
        return openParenthesis+child.toString()+closeParenthesis;
    }
}
