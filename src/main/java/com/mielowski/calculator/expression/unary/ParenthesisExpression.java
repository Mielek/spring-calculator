package com.mielowski.calculator.expression.unary;

import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class ParenthesisExpression extends UnaryExpression {

    private char openParenthesis;
    private char closeParenthesis;


    public ParenthesisExpression(Expression child, char openParenthesis, char closeParenthesis) {
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
