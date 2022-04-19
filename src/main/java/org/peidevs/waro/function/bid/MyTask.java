package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.function.Supplier;
import java.util.concurrent.Callable;

class MyTask implements Callable<Bid> {
    private Supplier<Bid> task;

    public MyTask(Supplier<Bid> task) {
        this.task = task;
    }

    @Override
    public Bid call() {
        return task.get();
    }
}
