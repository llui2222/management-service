package com.tpam.service.management.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class StrategySymbol {

    private final String symbolName;
    private final BigDecimal maxInstrumentsPerSingleTrade;
    private final Integer tradeDelayInMillis;

    @JsonCreator
    public StrategySymbol(@JsonProperty("symbolName")final String symbolName,
                          @JsonProperty("maxInstrumentsPerSingleTrade") final BigDecimal maxInstrumentsPerSingleTrade,
                          @JsonProperty("tradeDelayInMillis") final Integer tradeDelayInMillis) {
        this.symbolName = symbolName;
        this.maxInstrumentsPerSingleTrade = maxInstrumentsPerSingleTrade;
        this.tradeDelayInMillis = tradeDelayInMillis;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public BigDecimal getMaxInstrumentsPerSingleTrade() {
        return maxInstrumentsPerSingleTrade;
    }

    public Integer getTradeDelayInMillis() {
        return tradeDelayInMillis;
    }
}
