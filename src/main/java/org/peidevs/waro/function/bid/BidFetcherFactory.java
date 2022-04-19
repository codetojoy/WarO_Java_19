package org.peidevs.waro.function.bid;

public class BidFetcherFactory {
    public final static int CLASSIC = 1;
    public final static int LOOM_V1 = 2;
    public final static int LOOM_V2 = 3;

    public BidFetcher build(int type) {
        switch (type) {
            case CLASSIC: 
                return new BidFetcherClassicImpl();
            case LOOM_V1: 
                return new BidFetcherLoomImpl();
            default:
                throw new IllegalArgumentException("unknown bid fetcher");
        }
    }
}
