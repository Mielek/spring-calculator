package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.CommandHandler;
import com.mielowski.calculator.core.Expression;

import java.math.BigDecimal;

public class EvaluateExpressionHandler implements CommandHandler<EvaluateExpressionCommand, BigDecimal> {
    @Override
    public BigDecimal handle(EvaluateExpressionCommand command) {
        Expression expression = new ExpressionParser(command.expression).parse();
        return expression.result();
    }
}
