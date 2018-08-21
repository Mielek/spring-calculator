package com.mielowski.calculator.expression.chain;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionTokenizer;

public abstract class ExpressionFactoryChain {

    protected ExpressionFactoryChain nextInChain;

    public ExpressionFactoryChain(){}

    public ExpressionFactoryChain(ExpressionFactoryChain nextInChain) {
        this.nextInChain = nextInChain;
    }

    public void setNextInChain(ExpressionFactoryChain nextInChain) {
        this.nextInChain = nextInChain;
    }

    public abstract Expression parse(ExpressionTokenizer tokenizer);

}
