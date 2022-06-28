package org.peidevs.waro.function;

import org.peidevs.waro.function.bid.*;
import org.peidevs.waro.player.*;
import org.peidevs.waro.table.*;
import org.peidevs.waro.util.Log;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import static java.util.Comparator.comparing;

public class Round implements UnaryOperator<Stream<Player>> {
    private final int prizeCard;
    private final Log logger;

    public Round(int prizeCard, Log logger) {
        this.prizeCard = prizeCard;
        this.logger = logger;
    }

    protected Round(int prizeCard) {
        this.prizeCard = prizeCard;
        this.logger = new Log();
    }

    protected Round() {
        this.prizeCard = -1;
        this.logger = null;
    }

    @Override
    public Stream<Player> apply(Stream<Player> players) {
        var bids = getAllBids(players, prizeCard);

        logger.log(bids);

        var winningBid = findWinningBid(bids);

        var newWinner = winningBid.bidder().winsRound(winningBid);

        var winner = winningBid.bidder();
        var winnerName = winner.getName();

        logger.log(winner, prizeCard);

        var result = Stream.concat( Stream.of(newWinner),
                                    bids.stream()
                                        .filter(b -> ! b.bidder().getName().equals(winnerName))
                                        .map(b -> b.bidder().losesRound(b)));

        return result;
    }

    // --------- internal

    protected List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        var bidFetcher = new BidFetcherFactory().build(BidFetcherType.LOOM_V1_1);
        System.out.println("TRACER BidFetcher : " + bidFetcher.getClass().getSimpleName());
        var bids = bidFetcher.getAllBids(players, prizeCard);
        return bids;
    }

    protected Bid findWinningBid(List<Bid> bids) {
        var winningBid = bids.stream().max( comparing(Bid::offer) ).get();
        return winningBid;
    }
}
