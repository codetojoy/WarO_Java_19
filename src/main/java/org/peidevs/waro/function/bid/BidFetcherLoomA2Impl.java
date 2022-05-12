package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;

public class BidFetcherLoomA2Impl implements BidFetcher {

    /*
    public List<Bid> getAllBidsSwitch(Stream<Player> players, int prizeCard) {
        var mode = 2;

        try {
            if (mode == 1) {
                return getAllBidsOk(players, prizeCard);
            } else if (mode == 2) {
                return getAllBidsAutoCloseable(players, prizeCard);
            }
            } catch (InterruptedException ex) {
            System.err.println("TRACER A2 caught iex");
        }

        return null;
    }
    */

    @Override
    public List<Bid> getAllBids(Stream<Player> players, int prizeCard) {
        List<Future<Bid>> futures = null;

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Player -> Supplier<Bid> -> MyTask
            var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)));

            // we don't want a stream or else concurrent tasks aren't called
            // until evaluated, i.e. too late
            futures = tasks.map(t -> executor.submit(t)).collect(toList());
        }
        // executor is closed/shutdown here

        var bidGetter = new BidGetter();
        var bids = futures.stream().map(bidGetter::myGet).collect(toList());
        /*
        var bids = new ArrayList<Bid>();
        for (var future : futures) {
            bids.add(bidGetter.myGet(future));
        }
        */

        return bids;
    }

    // this wasn't working because streams are lazy-eval !!!
    /*
    public List<Bid> getAllBidsOk(Stream<Player> players, int prizeCard) throws InterruptedException {

        System.out.println("TRACER A2 toronto st patricks");

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<Bid>> futures = null;
        // try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        try {
            // Player -> Supplier<Bid> -> MyTask
            var tasks = players.map(p -> new MyTask(p.getStrategy(prizeCard)));

            futures = tasks.map(t -> executor.submit(t)).collect(toList());

        System.out.println("TRACER A2 montreal maroons");
        } catch (Exception ex) {
            exitAsFailure("BFLI A2", ex);
        }
        System.out.println("TRACER A2 hartford whalers");

            executor.shutdown(); // Disable new tasks from being submitted
            executor.awaitTermination(5, TimeUnit.SECONDS);


        List<Bid> bids = new ArrayList<Bid>();

        try {
            var bidGetter = new BidGetter();
            for (var future : futures) {
                bids.add(bidGetter.myGet(future));
            }
        /*
        System.out.println("TRACER A2 Impl cp abc");
            bids = futures.stream().map(bidGetter::myGet);
        } catch (Exception ex) {
            exitAsFailure("BFLI A2", ex);
        }

        return bids;
    }
    void exitAsFailure(String msg, Exception ex) {
        System.err.println("TRACER " + msg + " caught exception: " + ex);
        System.exit(-1); // just bail out ¯\_(ツ)_/¯
    }
    */
}
