package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;
import org.peidevs.waro.strategy.*;
import org.peidevs.waro.table.Hand;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

// INTEGRATION TEST
// requires running server
public class BidFetcherLoomBImplTest {

    // TODO: DRY
    // API remote strategy
    private static final String SCHEME = "http";
    private static final String HOST = "localhost:8080";
    private static final String PATH = "waro/strategy";
    private static final String MODE = "max";

    @Test
    public void testGetAllBids_Basic() {
        int prizeCard = 18;
        int maxCard = 80;

        var hand = new Hand(List.of(66,50));
        var player = new Player("Chopin", new ApiRemote(SCHEME, HOST, PATH, MODE), maxCard, hand);

        var players = new ArrayList<Player>(List.of(player)).stream();

        var bidFetcher = new BidFetcherFactory().build(BidFetcherType.LOOM_V2);

        // test
        var bids = bidFetcher.getAllBids(players, prizeCard);

        assertEquals(1, bids.size());
    }
}
