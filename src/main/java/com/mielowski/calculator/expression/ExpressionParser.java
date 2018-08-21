package com.mielowski.calculator.expression;


import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.chain.FullExpressionFactory;

class ExpressionParser {
    private FullExpressionFactory factory = new FullExpressionFactory();

    private String expression;
    private ExpressionTokenizer tokenizer;

    ExpressionParser(String expression) {
        this.expression = expression;
    }

    Expression parse() {
        tokenizer = new ExpressionTokenizer(expression);
        throwIfExpressionIsEmpty();
        Expression result = factory.create(tokenizer);
        throwIfUnknownEnding();
        return result;
    }

    private void throwIfExpressionIsEmpty() {
        if (tokenizer.isEmpty())
            throw new ExpressionParserException("Data is empty");
    }

    private void throwIfUnknownEnding() {
        if (tokenizer.hasNext())
            throw new ExpressionParserException("Unexpected expression ending " + tokenizer.getUnconsumedString());
    }
}
