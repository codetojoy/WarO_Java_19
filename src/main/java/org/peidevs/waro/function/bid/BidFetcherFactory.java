package org.peidevs.waro.function.bid;

public class BidFetcherFactory {
    public final static int CLASSIC = 0;
    public final static int LOOM_V1 = 1;
    public final static int LOOM_V2 = 2;
    public final static int LOOM_V3 = 3;
    public final static int LOOM_V4 = 4;

    public BidFetcher build(int type) {
        switch (type) {
            case CLASSIC:
                return new BidFetcherClassicImpl();
            case LOOM_V1:
                return new BidFetcherLoomExecutorsImpl();
            case LOOM_V2:
                return new BidFetcherLoomBImpl();
            case LOOM_V3:
                return new BidFetcherLoomCImpl();
            case LOOM_V4:
                return new BidFetcherLoomDImpl();
            default:
                throw new IllegalArgumentException("unknown bid fetcher");
        }
    }
}
