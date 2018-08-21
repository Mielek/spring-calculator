package com.mielowski.calculator.expression;


import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.chain.FullExpressionFactory;

class ExpressionParser {
    private String expression;
    private ExpressionTokenizer tokenizer;
    private FullExpressionFactory parser = new FullExpressionFactory();

    ExpressionParser(String expression) {
        this.expression = expression;
    }

    Expression parse() {
        tokenizer = new ExpressionTokenizer(expression);
        throwIfExpressionIsEmpty();
        Expression result = parser.create(tokenizer);
        throwIfUnknownEnding();
        return result;
    }

    private void throwIfExpressionIsEmpty() {
        if (!tokenizer.hasNext())
            throw new ExpressionParserException("Data is empty");
    }

    private void throwIfUnknownEnding() {
        if (tokenizer.hasNext())
            throw new ExpressionParserException("Unexpected expression ending " + tokenizer.getUnconsumedString());
    }
}
