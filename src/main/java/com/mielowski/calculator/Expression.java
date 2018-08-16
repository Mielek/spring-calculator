package com.mielowski.calculator;

import java.math.BigDecimal;

@FunctionalInterface
public interface Expression {
    BigDecimal result();
}
