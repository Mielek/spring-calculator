package com.mielowski.calculator.operation;

public abstract class TwoValueOperation implements Operation {

    protected Operation left;
    protected Operation right;

    public TwoValueOperation(Operation left, Operation right) {
        this.left = left;
        this.right = right;
    }
}
