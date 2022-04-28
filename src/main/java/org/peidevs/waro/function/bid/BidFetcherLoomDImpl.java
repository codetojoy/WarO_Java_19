package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
// import java.util.function.Supplier;
// import static java.util.stream.Collectors.toList;

public class BidFetcherLoomDImpl implements BidFetcher {
    private ConcurrentHashMap<Long, Bid> resultMap = new ConcurrentHashMap<>();

    @Override
    public List<Bid> getAllBids(Stream<Player> players, final int prizeCard) {
        // UGH
        final var playerList = new ArrayList<Integer>();
        players.forEach(player -> {
            final var thisPlayer = player;
            Thread.startVirtualThread(() ->  {
                var strategy = thisPlayer.getStrategy(prizeCard);
                // var task = new MyTask(strategy);
                var bid = strategy.get();
                var id = Thread.currentThread().getId();
                resultMap.put(id, bid);
            });
            playerList.add(1);
        });
        var playerCount = playerList.size();

        var isDone = false;
        while (!isDone) {
            try { Thread.sleep(50); } catch (Exception ex) {}
            isDone = resultMap.keySet().size() == playerCount;
        }

        var bids = new ArrayList<Bid>();
        for (var key : resultMap.keySet()) {
            var bid = resultMap.get(key);
            bids.add(bid);
        }

        return bids;
    }
}
