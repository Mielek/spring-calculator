package com.mielowski.calculator.integrate;

import com.mielowski.calculator.core.CommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntegrateHandler implements CommandHandler<IntegrateCommand, Double> {
    @Override
    public Double handle(IntegrateCommand command) {
        checkSplits(command.getSplits());
        ExecutorService executor = getExecutor(command.getThreads());
        try {
            return createSplit(command).stream()
                    .map(executor::submit)
                    .map(doubleFuture -> {
                        try{
                            return doubleFuture.get();
                        } catch (Exception e){
                            throw new IntegrateException("Job failure", e);
                        }
                    })
                    .reduce(0.0, (left, right) -> left+right);
        }finally {
            executor.shutdown();
        }
    }

    private void checkSplits(int splits) {
        if(splits<=0) throw new IntegrateException("Split must be at least 1");
    }

    private ExecutorService getExecutor(int threads) {
        if(threads<=0) throw new IntegrateException("Threads number must be at least 1");
        return Executors.newFixedThreadPool(threads);
    }

    private List<IntegrateJob> createSplit(IntegrateCommand command) {
        List<IntegrateJob> integralJobs = new ArrayList<>();
        double splitRage = (Math.abs(command.getEndRange()) - Math.abs(command.getStartRange())) / command.getSplits();
        double start = command.getStartRange();
        for(int i =0; i<command.getSplits(); ++i){
            double end = start + splitRage;
            integralJobs.add(new IntegrateJob(start, end));
            start = end;
        }
        return integralJobs;
    }
}
