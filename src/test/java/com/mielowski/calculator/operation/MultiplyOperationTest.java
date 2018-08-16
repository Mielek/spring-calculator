package com.mielowski.calculator.operation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiplyOperationTest {
    @DisplayName("Multiply values:")
    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({"1, 1, 1", "1, 2, 2", "2, 2, 4", "23, 32, 736", "-1, 55, -55", "4.0, 0.5, 2.00"})
    public void xMultipliedByYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z){
        BigDecimal result = new MultiplyOperation(ConstantOperation.of(x), ConstantOperation.of(y)).result();

        assertThat(result).isEqualTo(z);
    }
}
