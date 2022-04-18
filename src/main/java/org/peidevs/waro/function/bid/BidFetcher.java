package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;
import org.peidevs.waro.table.*;

import java.util.*;
import java.util.stream.*;

public interface BidFetcher {
     List<Bid> getAllBids(Stream<Player> players, int prizeCard);
}
