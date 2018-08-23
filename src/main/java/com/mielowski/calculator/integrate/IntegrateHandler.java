package com.mielowski.calculator.integrate;

import com.mielowski.calculator.core.CommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegrateHandler implements CommandHandler<IntegrateCommand, Double> {
    @Override
    public Double handle(IntegrateCommand command) {
        checkSplits(command.getSplits());
        ExecutorService executor = getExecutor(command.getThreads());
        RangeGenerator rangeGenerator = new RangeGenerator(command);
        List<Future<Double>> partials = new ArrayList<>();
        for (int i = 0; i < command.getThreads(); ++i) {
            partials.add(executor.submit(new CalculateJob(rangeGenerator)));
        }
        try {
            return partials.stream().map(doubleFuture -> {
                try {
                    return doubleFuture.get();
                } catch (Exception e) {
                    throw new IntegrateException("Job failure", e);
                }
            }).reduce(0.0, (left, right) -> left + right);
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
        //return new ForkJoinPool(threads);
        return Executors.newFixedThreadPool(threads);
    }

    private static class Range {
        double start;
        double end;

        Range(double start, double end) {
            this.start = start;
            this.end = end;
        }
    }

    private static class CalculateJob implements Callable<Double> {
        RangeGenerator generator;

        CalculateJob(RangeGenerator generator) {
            this.generator = generator;
        }

        @Override
        public Double call() {
            Range range;
            double result = 0.0;
            while ((range = generator.emit()) != null)
                result += Math.exp(range.end) - Math.exp(range.start);
            return result;
        }
    }

    private static class RangeGenerator {
        IntegrateCommand command;
        double splitRage;
        AtomicInteger index = new AtomicInteger(0);

        RangeGenerator(IntegrateCommand command) {
            this.command = command;
            splitRage = (command.getEndRange() - command.getStartRange()) / command.getSplits();
        }

        Range emit() {
            int andIncrement = index.getAndIncrement();
            if (andIncrement < command.getSplits()) {
                double start = command.getStartRange() + splitRage * andIncrement;
                return new Range(start, start + splitRage);
            }
            return null;
        }
    }
}
