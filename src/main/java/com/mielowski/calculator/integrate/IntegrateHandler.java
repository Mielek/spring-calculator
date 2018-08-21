package com.mielowski.calculator.integrate;

import com.mielowski.calculator.core.CommandHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class IntegrateHandler implements CommandHandler<IntegrateCommand, Double> {
    @Override
    public Double handle(IntegrateCommand command) {
        checkSplits(command.getSplits());
        ExecutorService executor = getExecutor(command.getThreads());
        try {
            return executor.submit(() ->
                    createSplit(command)
                            .parallel()
                            .map(range -> Math.exp(range.stop) - Math.exp(range.start))
                            .reduce(0.0, (left, right) -> left + right))
                    .get();
        } catch (Exception e) {
            throw new IntegrateException("Job failure", e);
        } finally {
            executor.shutdown();
        }
    }

    private void checkSplits(int splits) {
        if (splits <= 0) throw new IntegrateException("Split must be at least 1");
    }

    private ExecutorService getExecutor(int threads) {
        if (threads <= 0) throw new IntegrateException("Threads number must be at least 1");
        return new ForkJoinPool(threads);

    }

    private Stream<Range> createSplit(IntegrateCommand command) {
        double splitRage = (command.getEndRange() - command.getStartRange()) / command.getSplits();
        return Stream.iterate(
                new Range(command.getStartRange(), splitRage),
                range -> range.stop + splitRage < command.getEndRange(),
                range -> new Range(range.stop, range.stop + splitRage));
    }

    private static class Range {
        double start;
        double stop;

        public Range(double start, double stop) {
            this.start = start;
            this.stop = stop;
        }
    }
}
