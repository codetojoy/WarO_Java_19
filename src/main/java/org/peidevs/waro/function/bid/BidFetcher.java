package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.List;
import java.util.stream.Stream;

public interface BidFetcher {
     List<Bid> getAllBids(Stream<Player> players, int prizeCard);
}
