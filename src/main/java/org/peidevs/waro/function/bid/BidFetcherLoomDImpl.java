package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class BidFetcherLoomDImpl implements BidFetcher {
    private ConcurrentHashMap<Long, Bid> resultMap = new ConcurrentHashMap<>();

    @Override
    public List<Bid> getAllBids(Stream<Player> players, final int prizeCard) {
        final var playerCount = new AtomicInteger();
        players.forEach(player -> {
            final var thisPlayer = player;
            Thread.startVirtualThread(() ->  {
                var strategy = thisPlayer.getStrategy(prizeCard);
                var bid = strategy.get();
                var id = Thread.currentThread().getId();
                resultMap.put(id, bid);
            });
            playerCount.incrementAndGet();
        });

        var isDone = false;
        while (!isDone) {
            final var DELAY_IN_MILLIS = 100L;
            try { Thread.sleep(DELAY_IN_MILLIS); } catch (Exception ex) {}
            isDone = resultMap.keySet().size() == playerCount.intValue();
        }

        // TODO
        var bids = new ArrayList<Bid>();
        for (var key : resultMap.keySet()) {
            var bid = resultMap.get(key);
            bids.add(bid);
        }

        return bids;
    }
}
