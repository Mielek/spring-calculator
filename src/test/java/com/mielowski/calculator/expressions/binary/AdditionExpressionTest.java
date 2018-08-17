package com.mielowski.calculator.expressions.binary;

import static org.assertj.core.api.Assertions.*;

import com.mielowski.calculator.expressions.ValueExpression;
import com.mielowski.calculator.expressions.binary.AdditionExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

public class AdditionExpressionTest {

    @DisplayName("Addiction of values:")
    @ParameterizedTest(name = "{0} + {1} = {3}")
    @CsvSource({
            "1, 1, 2",
            "2, 2, 4",
            "1.23, 8.77, 10",
            "10, -20, -10",
            "0.5, -1, -0.5"
    })
    public void xAddedToYEqualsZ(BigDecimal x, BigDecimal y, BigDecimal z) {
        BigDecimal result = new AdditionExpression(ValueExpression.of(x), ValueExpression.of(y)).result();

        assertThat(result).isEqualByComparingTo(z);
    }

}