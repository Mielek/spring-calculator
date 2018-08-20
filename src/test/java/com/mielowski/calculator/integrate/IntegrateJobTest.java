package com.mielowski.calculator.integrate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class IntegrateJobTest {

    @DisplayName("Integrate e^x:")
    @ParameterizedTest(name = "From {0} To {1} = {2}")
    @CsvSource({
            "0, 1, 1.71828182",
            "1, 0, -1.71828182",
            "0.5, 1, 1.06956",
            "0, 0.5, 0.648721",
            "-1, 1, 2.350402"
    })
    public void integrateFromXToYEqualsZ(Double x, Double y, Double z){
        Double result = new IntegrateJob(x, y).call();
        assertThat(result).isEqualTo(z, offset(0.000001));
    }
}
