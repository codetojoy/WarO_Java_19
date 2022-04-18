package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;
import org.peidevs.waro.table.*;
import org.peidevs.waro.util.Log;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.util.function.*;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparing;

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

public class BidFetcherLoomImpl implements BidFetcher {

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        var executorService = Executors.newVirtualThreadPerTaskExecutor();
        // Player -> Supplier<Bid> -> MyTask
        var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)))
                                                      .collect(toList());
        var futures = new ArrayList<Future<Bid>>();
        for (var task : tasks) {
            futures.add(executorService.submit(task));
        }
        executorService.shutdown(); // Disable new tasks from being submitted

        var bids = new ArrayList<Bid>();

        try {
            System.out.println("TRACER fetching via v-threads...");
            executorService.awaitTermination(5, TimeUnit.SECONDS);

            for (var future : futures) {
                bids.add(future.get());
            }
            // var bids = futures.stream().map(f -> f.get().collect(toList()));
        } catch (Exception ex) {
            System.err.println("TRACER BFLI !!! caught exception: " + ex);
            // just bail out ¯\_(ツ)_/¯
            System.exit(-1);
        }

        return bids;
    }
}
