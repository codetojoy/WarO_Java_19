package org.peidevs.waro.strategy;

import java.util.List;
import java.util.stream.*;
import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public final class ApiRemote implements Strategy {
    private static final String CARDS_PARAM = "cards";
    private static final String MODE_PARAM = "mode";
    private static final String PRIZE_CARD_PARAM = "prize_card";
    private static final String MAX_CARD_PARAM = "max_card";

    private String scheme;
    private String host;
    private String path;
    private String mode;

    public ApiRemote(String scheme, String host, String path, String mode) {
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.mode = mode;
    }

    @Override
    public int selectCard(int prizeCard, IntStream hand, int maxCard) {
        var bid = 0;

        try {
             bid = apiRemoteSelectCard(prizeCard, hand, maxCard);
        } catch (Exception ex) {
            System.err.println("ERROR caught exception: " + ex.getMessage());
            // bail out for now
            System.exit(-1);
        }

        return bid;
    }

    private int apiRemoteSelectCard(int prizeCard, IntStream hand, int maxCard) throws Exception {
        var card = 0;
        var uri = buildURI(prizeCard, hand, maxCard);

        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity);
                System.out.println("TRACER api remote: " + resultStr);
                ApiResult apiResult = buildResult(resultStr);
                card = apiResult.getCard();
            }
        }

        return card;
    }

    private URI buildURI(int prizeCard, IntStream hand, int maxCard) throws Exception {
        URIBuilder builder = new URIBuilder();

        builder.setScheme(scheme)
               .setHost(host)
               .setPath(path)
               .setParameter(MODE_PARAM, mode)
               .setParameter(PRIZE_CARD_PARAM, "" + prizeCard)
               .setParameter(MAX_CARD_PARAM, "" + maxCard);

        List<String> cardsStrings = hand.boxed()
                                        .map(c -> "" + c)
                                        .toList();

        var cardsQueryValue = String.join(",", cardsStrings);

        builder.setParameter(CARDS_PARAM, cardsQueryValue);

        URI uri = builder.build();

        return uri;
    }

    protected ApiResult buildResult(String str) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ApiResult result = mapper.readValue(str, ApiResult.class);
        return result;
    }
}

class ApiResult {
    private int card;
    private String message;

    public int getCard() { return card; }
    public void setCard(int card) { this.card = card; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String toString() {
        return "card: " + card + " message: " + message;
    }
}
