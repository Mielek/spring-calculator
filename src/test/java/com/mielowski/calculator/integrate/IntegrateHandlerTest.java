package com.mielowski.calculator.integrate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class IntegrateHandlerTest {

    private IntegrateHandler handler = new IntegrateHandler();

    @DisplayName("Integrate e^x:")
    @ParameterizedTest(name = "From {0} To {1} = {2} with {3} threads and {4} splits")
    @CsvSource({
            "0, 1, 1.71828182, 1, 1",
            "0, 1, 1.71828182, 1, 50",
            "1, 0, -1.71828182, 8, 50",
            "0.5, 1, 1.06956, 4, 4",
            "0, 0.5, 0.648721, 40, 1",
            "-1, 1, 2.350402, 4, 100"
    })
    public void integrate(double start, double end, double expected, int threads, int splits){
        IntegrateCommand command = createCommand(start, end, threads, splits);
        Double result = handler.handle(command);
        assertThat(result).isEqualTo(expected, offset(0.000001));
    }

    private IntegrateCommand createCommand(double start, double end, int threads, int splits) {
        IntegrateCommand command = new IntegrateCommand();
        command.setStartRange(start);
        command.setEndRange(end);
        command.setThreads(threads);
        command.setSplits(splits);
        return command;
    }

}
