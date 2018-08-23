package com.mielowski.calculator.expression.factory;

abstract class ExpressionFactoryChain implements ExpressionFactory {
    protected ExpressionFactory nextInChain;

    void setNextFactory(ExpressionFactory nextInChain) {
        this.nextInChain = nextInChain;
    }
}
