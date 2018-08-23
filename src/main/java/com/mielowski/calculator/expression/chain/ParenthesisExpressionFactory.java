package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.unary.ParenthesisExpression;

import java.util.HashMap;
import java.util.Map;

public class ParenthesisExpressionFactory extends UnaryExpressionFactory {
    private Map<String, String> startToEndParenthesis = new HashMap<>();
    private ExpressionFactory innerExpression;

    ParenthesisExpressionFactory() {
        startToEndParenthesis.put("(", ")");
        startToEndParenthesis.put("{", "}");
        startToEndParenthesis.put("[", "]");
        addUnaryFunctionCreator("(", expression -> new ParenthesisExpression(expression, "(", ")"));
        addUnaryFunctionCreator("{", expression -> new ParenthesisExpression(expression, "{", "}"));
        addUnaryFunctionCreator("[", expression -> new ParenthesisExpression(expression, "[", "]"));
    }

    void setInnerExpressionFactory(ExpressionFactory innerExpression) {
        this.innerExpression = innerExpression;
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        ExpressionTokenizer.Token token = tokenizer.getToken();
        if (token.isOperation() && startToEndParenthesis.containsKey(token.getValue())) {
            tokenizer.nextToken();
            Expression expression = getCreator(token.getValue()).apply(innerExpression.create(tokenizer));
            eatEndingParenthesis(tokenizer, token.getValue());
            return expression;
        }
        return nextInChain.create(tokenizer);
    }

    private void eatEndingParenthesis(ExpressionTokenizer tokenizer, String openParenthesis) {
        String ending = startToEndParenthesis.get(openParenthesis);
        ExpressionTokenizer.Token token = tokenizer.getToken();
        if (!token.getValue().equals(ending))
            throw new ExpressionFactoryException("No ending parenthesis for " + openParenthesis + " instead have " + token.getValue());
        tokenizer.nextToken();
    }
}
