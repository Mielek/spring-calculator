package com.mielowski.calculator.expressions.binary;

import com.mielowski.calculator.expressions.ValueExpression;
import com.mielowski.calculator.expressions.binary.MultiplyExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiplyExpressionTest {
    @DisplayName("Multiply values:")
    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({"1, 1, 1", "1, 2, 2", "2, 2, 4", "23, 32, 736", "-1, 55, -55", "4, 0.5, 2"})
    public void xMultipliedByYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z){
        BigDecimal result = new MultiplyExpression(ValueExpression.of(x), ValueExpression.of(y)).result();

        assertThat(result).isEqualByComparingTo(z);
    }
}
