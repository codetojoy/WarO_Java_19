package org.peidevs.waro.function.bid;

public class BidFetcherFactory {
    public final static int CLASSIC = 0;
    public final static int LOOM_V1 = 1;
    public final static int LOOM_V2 = 2;
    public final static int LOOM_V3 = 3;
    public final static int LOOM_V4 = 4;

    public BidFetcher build(int type) {
        BidFetcher result = null;
        switch (type) {
            case CLASSIC:
                result = new BidFetcherClassicImpl();
                break;
            case LOOM_V1:
                result = new BidFetcherLoomExecutorsImpl();
                break;
            case LOOM_V2:
                result = new BidFetcherLoomBImpl();
                break;
            case LOOM_V3:
                result = new BidFetcherLoomCImpl();
                break;
            case LOOM_V4:
                result = new BidFetcherLoomDImpl();
                break;
            default:
                throw new IllegalArgumentException("unknown bid fetcher");
        }
        System.err.println("TRACER BidFetcher : " + result.getClass().getSimpleName());
        return result;
    }
}
