package com.mielowski.calculator.expression.chain;

abstract class ExpressionFactoryChain implements ExpressionFactory {
    protected ExpressionFactory nextInChain;

    void setNextFactory(ExpressionFactory nextInChain) {
        this.nextInChain = nextInChain;
    }
}
