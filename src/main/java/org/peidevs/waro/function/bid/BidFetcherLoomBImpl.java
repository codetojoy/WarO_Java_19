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

        var bidGetter = new BidGetter();
        List<Bid> bids = futures.map(bidGetter::myGet).collect(toList());

        return bids;
    }
}
