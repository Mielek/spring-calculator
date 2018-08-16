package com.mielowski.calculator.operation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DivisionExpressionTest {
    @DisplayName("Divide values:")
    @ParameterizedTest(name = "{0} / {1} = {2}")
    @CsvSource({"1, 1, 1", "1, 2, 0.5", "1, 0.5, 2"})
    public void xDividedByYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z){
        BigDecimal result = new DivisionExpression(ConstantExpression.of(x), ConstantExpression.of(y)).result();

        assertThat(result).isEqualByComparingTo(z);
    }

    @Test
    public void exceptionWhenDividedBy0(){
        assertThatThrownBy(new DivisionExpression(ConstantExpression.of(1.0), ConstantExpression.of(0.0))::result)
                .isExactlyInstanceOf(ArithmeticException.class);
    }
}
