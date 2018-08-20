package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Command;

public class ExpressionCommand implements Command {
    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
