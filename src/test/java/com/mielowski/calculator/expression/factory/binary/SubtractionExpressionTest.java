package com.mielowski.calculator.expression.factory.binary;

import com.mielowski.calculator.expression.factory.ValueExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SubtractionExpressionTest {
    @DisplayName("Subtraction of values:")
    @ParameterizedTest(name = "{0} - {1} = {2}")
    @CsvSource({"1, 1, 0", "2, 1, 1", "100.05, 0.05, 100", "25, -25, 50"})
    public void xSubtractedByYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z){
        BigDecimal result = new SubtractionExpression(new ValueExpression(x), new ValueExpression(y)).result();

        assertThat(result).isEqualByComparingTo(z);
    }
}
