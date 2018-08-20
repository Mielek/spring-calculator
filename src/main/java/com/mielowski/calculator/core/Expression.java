package com.mielowski.calculator.core;

import java.math.BigDecimal;

@FunctionalInterface
public interface Expression {
    BigDecimal result();
}
