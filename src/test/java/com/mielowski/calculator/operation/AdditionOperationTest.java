package com.mielowski.calculator.operation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

public class AdditionOperationTest {

    @DisplayName("Addiction of values:")
    @ParameterizedTest(name = "{0} + {1} = {3}")
    @CsvSource({
            "1, 1, 2",
            "2, 2, 4",
            "1.23, 8.77, 10.00",
            "10, -20, -10",
            "0.5, -1, -0.5"
    })
    public void xAddedToYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z) {
        BigDecimal result = new AdditionOperation(ValueOperation.of(x), ValueOperation.of(y)).result();

        assertThat(result).isEqualTo(z);
    }

}
