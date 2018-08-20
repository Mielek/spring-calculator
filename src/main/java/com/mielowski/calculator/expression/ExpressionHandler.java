package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.CommandHandler;
import com.mielowski.calculator.core.Expression;

public class ExpressionHandler implements CommandHandler<ExpressionCommand, Expression> {
    @Override
    public Expression handle(ExpressionCommand command) {
        return new ExpressionParser(command.expression).parse();
    }
}
