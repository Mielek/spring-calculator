package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;
import com.mielowski.calculator.expressions.unary.ParenthesisExpression;

import java.util.function.Supplier;

public class ParenthesisExpressionFactory {

    private Supplier<Expression> subExpressionSupplier;

    public static int[] getStartingParentheses() {
        return new int[]{'(', '[', '{'};
    }

    public static char getEndingParenthesesFor(int parenthesis) {
        switch (parenthesis) {
            case '(':
                return ')';
            case '[':
                return ']';
            case '{':
                return '}';
            default:
                throw new ExpressionFactoryException("Unknown ending parentheses: " + parenthesis);
        }
    }

    public static ParenthesisExpressionFactory create() {
        return new ParenthesisExpressionFactory();
    }

    public ParenthesisExpressionFactory setSubExpressionSupplier(Supplier<Expression> subExpressionSupplier) {
        this.subExpressionSupplier = subExpressionSupplier;
        return this;
    }

    public Expression build(ExpressionTokenizer tokenizer){
        char openParenthesis = tokenizer.returnLastAndMove();
        char ending = ParenthesisExpressionFactory.getEndingParenthesesFor(openParenthesis);
        Expression x = subExpressionSupplier.get();
        if (tokenizer.getCurrentToken() != ending)
            throw new ExpressionFactoryException("No ending parenthesis for " + openParenthesis+ " instead have " + tokenizer.getCurrentToken());
        tokenizer.nextToken();
        return new ParenthesisExpression(x, openParenthesis, ending);
    }

}
