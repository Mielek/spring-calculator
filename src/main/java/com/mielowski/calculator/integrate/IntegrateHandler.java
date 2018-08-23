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
                    createStreamOfRanges(command)
                            .parallel()
                            .map(range -> Math.exp(range.end) - Math.exp(range.start))
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

    private Stream<Range> createStreamOfRanges(IntegrateCommand command) {
        Stream.Builder<Range> rangeStreamBuilder = Stream.builder();
        double splitRage = (command.getEndRange() - command.getStartRange()) / command.getSplits();
        double start = command.getStartRange();
        for (int i = 0; i < command.getSplits(); ++i) {
            Range range = new Range(start, start + splitRage);
            rangeStreamBuilder.accept(range);
            start = range.end;
        }
        return rangeStreamBuilder.build();
    }

    private static class Range {
        double start;
        double end;

        public Range(double start, double end) {
            this.start = start;
            this.end = end;
        }
    }
}
