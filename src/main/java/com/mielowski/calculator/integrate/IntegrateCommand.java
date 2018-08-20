package com.mielowski.calculator.integrate;

import com.mielowski.calculator.core.Command;

public class IntegrateCommand implements Command {
    private double startRange;
    private double endRange;
    private int threads;
    private int splits;

    public double getStartRange() {
        return startRange;
    }

    public void setStartRange(double startRange) {
        this.startRange = startRange;
    }

    public double getEndRange() {
        return endRange;
    }

    public void setEndRange(double endRange) {
        this.endRange = endRange;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getSplits() {
        return splits;
    }

    public void setSplits(int splits) {
        this.splits = splits;
    }

}
