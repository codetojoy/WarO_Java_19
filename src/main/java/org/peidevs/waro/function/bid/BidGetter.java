package org.peidevs.waro.function.bid;

import org.peidevs.waro.player.Bid;

import java.util.concurrent.*;

public class BidGetter {
    // lambdas have trouble with checked exceptions used in Future.get()
    // see https://dzone.com/articles/how-to-handle-checked-exception-in-lambda-expressi
    protected Bid myGet(Future<Bid> f) {
        Bid result = null;
        try {
            result = f.get();
        } catch (Exception ex) {
            exitAsFailure("BFLI 1", ex);
        }
        return result;
    }

    private void exitAsFailure(String msg, Exception ex) {
        System.err.println("TRACER " + msg + " caught exception: " + ex);
        System.exit(-1); // just bail out ¯\_(ツ)_/¯
    }
}
