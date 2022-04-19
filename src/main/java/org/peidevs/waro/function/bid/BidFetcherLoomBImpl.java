package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;

public class BidFetcherLoomBImpl implements BidFetcher {
    private ExecutorService executorService;

    BidFetcherLoomBImpl() {
        var threadFactory = Thread.ofVirtual().factory();
        executorService = Executors.newSingleThreadExecutor(threadFactory);
    }

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        // Player -> Supplier<Bid> -> MyTask
        var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)));
        var futures = tasks.map(t -> executorService.submit(t));

        List<Bid> bids = futures.map(this::myGet).collect(toList());

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
