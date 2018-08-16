package com.mielowski.calculator.operation;

import java.math.BigDecimal;

public class SquareRootExpression extends OneValueExpression {

    public SquareRootExpression(Expression child) {
        super(child);
    }

    @Override
    public BigDecimal result() {
        BigDecimal childResult = child.result();
        if(isNegativeValue(childResult))
            throw new ArithmeticException("Square root of negative value, "+childResult.toString());
        if(isZeroValue(childResult))
            return BigDecimal.ZERO;
        return sqrRoot(childResult);
    }

    private boolean isZeroValue(BigDecimal childResult) {
        return childResult.compareTo(BigDecimal.ZERO) == 0;
    }

    private boolean isNegativeValue(BigDecimal childResult) {
        return childResult.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * @url https://stackoverflow.com/a/16859436
     */
    private static BigDecimal sqrRoot(BigDecimal value){
        BigDecimal x = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
        return x.add(BigDecimal.valueOf(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
    }
}
