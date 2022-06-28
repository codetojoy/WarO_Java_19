package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class BidFetcherLoomA2Impl implements BidFetcher {

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        List<Future<Bid>> futures = null;

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Player -> Supplier<Bid> -> MyTask
            var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)));

            // we don't want a stream or else concurrent tasks aren't called
            // until evaluated, i.e. too late
            futures = tasks.map(t -> executor.submit(t)).toList();
        }
        // executor is closed/shutdown here

        var bidGetter = new BidGetter();
        var bids = futures.stream().map(bidGetter::myGet).toList();

        return bids;
    }
}
