package org.peidevs.waro.function.bid;

import org.peidevs.waro.function.bid.async.classic.BidFetcherClassic;
import org.peidevs.waro.player.*;

import java.util.List;
import java.util.stream.Stream;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparing;

public class BidFetcherClassicImpl implements BidFetcher {

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        List<Supplier<Bid>> tasks = players.map(p -> p.getStrategy(prizeCard))
                                                      .collect(toList());

        var bidFetcher = new BidFetcherClassic();
        var bids = bidFetcher.fetchBids(tasks);

        return bids;
    }
}
