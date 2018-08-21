package com.mielowski.calculator.expression.chain;

public abstract class ExpressionFactoryChain implements ExpressionFactory {
    protected ExpressionFactory nextInChain;

    public void setNextInChain(ExpressionFactory nextInChain) {
        this.nextInChain = nextInChain;
    }
}
