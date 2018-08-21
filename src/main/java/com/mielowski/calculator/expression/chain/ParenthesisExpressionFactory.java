package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionTokenizer;
import com.mielowski.calculator.expression.unary.ParenthesisExpression;

import java.util.HashMap;
import java.util.Map;

public class ParenthesisExpressionFactory extends UnaryExpressionFactory {
    private Map<Character, Character> startToEndParenthesis = new HashMap<>();
    private ExpressionFactory innerExpression;

    public ParenthesisExpressionFactory() {
        startToEndParenthesis.put('(', ')');
        startToEndParenthesis.put('{', '}');
        startToEndParenthesis.put('[', ']');
        addUnaryFunctionCreator("(", expression -> new ParenthesisExpression(expression, '(', ')'));
        addUnaryFunctionCreator("{", expression -> new ParenthesisExpression(expression, '{', '}'));
        addUnaryFunctionCreator("[", expression -> new ParenthesisExpression(expression, '[', ']'));
    }

    public void setInnerExpression(ExpressionFactory innerExpression) {
        this.innerExpression = innerExpression;
    }

    @Override
    public Expression create(ExpressionTokenizer tokenizer) {
        char operator = tokenizer.getCurrentToken();
        if(startToEndParenthesis.containsKey(operator)){
            Expression expression = getCreator(String.valueOf(tokenizer.getCurrentAndMove())).apply(innerExpression.create(tokenizer));
            eatEndingParenthesis(tokenizer, operator);
            return expression;
        }
        return nextInChain.create(tokenizer);
    }

    private void eatEndingParenthesis(ExpressionTokenizer tokenizer, char openParenthesis) {
        char ending = startToEndParenthesis.get(openParenthesis);
        if (tokenizer.getCurrentToken() != ending)
            throw new ExpressionFactoryException("No ending parenthesis for " + openParenthesis+ " instead have " + tokenizer.getCurrentToken());
        tokenizer.nextToken();
    }
}
