package com.mielowski.calculator.expressions;

import com.mielowski.calculator.expressions.unary.SquareRootExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class SquareRootExpressionTest {
    @DisplayName("Squared values:")
    @ParameterizedTest(name = "root({0}) = {1}")
    @CsvSource({"4, 2", "9, 3", "0.25, 0.5", "0, 0"})
    public void xSquaredEqualsZ(BigDecimal x, BigDecimal z){
        BigDecimal result = new SquareRootExpression(ValueExpression.of(x)).result();
        assertThat(result).isEqualByComparingTo(z);
    }

    @Test
    public void exceptionWhenValueIsNegative(){
        assertThatThrownBy(new SquareRootExpression(ValueExpression.of(-4.0))::result).isExactlyInstanceOf(ArithmeticException.class);
    }
}
