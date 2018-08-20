package com.mielowski.calculator.integrate;
import java.util.concurrent.Callable;

public class IntegrateJob implements Callable<Double> {

    private double exponentRangeStart;
    private double exponentRangeEnd;

    public IntegrateJob(double exponentRangeStart, double exponentRangeEnd) {
        this.exponentRangeStart = exponentRangeStart;
        this.exponentRangeEnd = exponentRangeEnd;
    }

    @Override
    public Double call() {
        double result = Math.exp(exponentRangeEnd) - Math.exp(exponentRangeStart);
        return result;
    }
}
