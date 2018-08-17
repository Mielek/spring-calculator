package com.mielowski.calculator.expressions;

import com.mielowski.calculator.Expression;
import com.mielowski.calculator.ExpressionTokenizer;

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
                throw new RuntimeException("Unknown ending parentheses: " + parenthesis);
        }
    }

    public static ParenthesisExpressionFactory create() {
        return new ParenthesisExpressionFactory();
    }

    public ParenthesisExpressionFactory setSubExpressionSuplier(Supplier<Expression> subExpressionSupplier) {
        this.subExpressionSupplier = subExpressionSupplier;
        return this;
    }

    public Expression build(ExpressionTokenizer tokenizer){
        char openParenthesis = (char) tokenizer.returnLastAndMove();
        char ending = ParenthesisExpressionFactory.getEndingParenthesesFor(openParenthesis);
        Expression x = subExpressionSupplier.get();
        if (tokenizer.getCurrentToken() != ending)
            throw new RuntimeException("No ending parenthesis for " + (char) openParenthesis+ " instead have " + ending);
        tokenizer.nextToken();
        return x;
    }

}
