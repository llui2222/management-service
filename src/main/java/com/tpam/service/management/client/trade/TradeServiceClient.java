package com.tpam.service.management.client.trade;

import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URI;

@Component
public class TradeServiceClient {

    private final TradeServiceRestClient tradeServiceRestClient;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TradeServiceClient(final TradeServiceRestClient tradeServiceRestClient) {
        this.tradeServiceRestClient = tradeServiceRestClient;
    }

    //TODO Uncomment to Integrate with trade service
    public URI submitTrade(final SubmitTradeRequest request) {
        //tradeServiceRestClient.submitTrade(request).getBody();
        return null;
    }

    //TODO Uncomment to Integrate with trade service
    public StrategySymbolExposure getStrategySymbolExposures(final String takerName, final String takerLogin, final String symbol) {
        //return tradeServiceRestClient.getStrategySymbolExposures(takerName, takerLogin, symbol);
        return new StrategySymbolExposure(BigDecimal.ZERO, BigDecimal.ZERO,takerName, takerLogin, symbol);
    }
}
