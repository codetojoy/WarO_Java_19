package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class BidFetcherLoomExecutorsImpl implements BidFetcher {

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        var executorService = Executors.newVirtualThreadPerTaskExecutor();
        // Player -> Supplier<Bid> -> MyTask
        var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)));
        var futures = tasks.map(t -> executorService.submit(t));

        List<Bid> bids = new ArrayList<Bid>();

        try {
            bids = futures.map(this::myGet).toList();

            executorService.shutdown(); // Disable new tasks from being submitted
            System.out.println("TRACER fetching via v-threads...");
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            exitAsFailure("BFLI 2", ex);
        }

        return bids;
    }

    // lambdas have trouble with checked exceptions used in Future.get()
    // see https://dzone.com/articles/how-to-handle-checked-exception-in-lambda-expressi
    protected Bid myGet(Future<Bid> f) {
        Bid result = null;
        try {
            result = f.get();
        } catch (Exception ex) {
            exitAsFailure("BFLI 1", ex);
        }
        return result;
    }

    void exitAsFailure(String msg, Exception ex) {
        System.err.println("TRACER " + msg + " caught exception: " + ex);
        System.exit(-1); // just bail out ¯\_(ツ)_/¯
    }
}
