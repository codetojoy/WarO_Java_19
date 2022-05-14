package org.peidevs.waro.function.bid;

public class BidFetcherFactory {
    public BidFetcher build(BidFetcherType type) {
        BidFetcher result = null;
        switch (type) {
            case CLASSIC -> result = new BidFetcherClassicImpl();
            case LOOM_V1 -> result = new BidFetcherLoomExecutorsImpl();
            case LOOM_V1_1 -> result = new BidFetcherLoomA2Impl();
            case LOOM_V2 -> result = new BidFetcherLoomBImpl();
            case LOOM_V3 -> result = new BidFetcherLoomCImpl();
            case LOOM_V4 -> result = new BidFetcherLoomDImpl();
        }
        System.err.println("TRACER BidFetcher : " + result.getClass().getSimpleName());
        return result;
    }
}
