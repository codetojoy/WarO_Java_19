package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class BidFetcherLoomDImpl implements BidFetcher {
    private ConcurrentHashMap<Long, Bid> resultMap = new ConcurrentHashMap<>();
    private final AtomicInteger playerCount = new AtomicInteger();

    @Override
    public List<Bid> getAllBids(Stream<Player> players, final int prizeCard) {
        players.forEach(player -> {
            Thread.startVirtualThread(() ->  {
                var id = Thread.currentThread().getId();
                var bid = player.getStrategy(prizeCard).get();
                resultMap.put(id, bid);
            });
            playerCount.incrementAndGet();
        });

        waitForResults();

        var bids = resultMap.values().stream().toList();

        return bids;
    }

    private void waitForResults() {
        var isDone = false;
        while (!isDone) {
            final var DELAY_IN_MILLIS = 100L;
            try { Thread.sleep(DELAY_IN_MILLIS); } catch (Exception ex) {}
            isDone = resultMap.keySet().size() == playerCount.intValue();
        }
    }
}
