package org.peidevs.waro.function.bid;

public class BidFetcherFactory {
    public BidFetcher build(BidFetcherType type) {
        return switch (type) {
            case CLASSIC -> new BidFetcherClassicImpl();
            case LOOM_V1 -> new BidFetcherLoomExecutorsImpl();
            case LOOM_V1_1 -> new BidFetcherLoomA2Impl();
            case LOOM_V2 -> new BidFetcherLoomBImpl();
            case LOOM_V3 -> new BidFetcherLoomCImpl();
            case LOOM_V4 -> new BidFetcherLoomDImpl();
        };
    }
}
