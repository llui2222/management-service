package com.tpam.service.management.client.trade.model;

import java.math.BigDecimal;

public class SubmitTradeRequest {
    private final String requestId;
    private final BigDecimal requestedVolume;
    private final BigDecimal requestedOpenPrice;
    private final String takerName;
    private final String takerLogin;
    private final String symbol;
    private final String side;
    private final String orderType;
    private final String timeInForce;
    private boolean submitted =false;

    public SubmitTradeRequest(final String requestId, final BigDecimal requestedVolume, final BigDecimal requestedOpenPrice, final String takerName, final String takerLogin, final String symbol, final boolean isBuy) {
        this.requestId = requestId;
        this.requestedVolume = requestedVolume;
        this.requestedOpenPrice = requestedOpenPrice;
        this.takerLogin = takerLogin;
        this.takerName = takerName;
        this.symbol = symbol;
        this.side = isBuy ? "BUY" : "SELL";
        this.orderType = "MARKET";
        this.timeInForce = "GTC";
    }

    public String getRequestId() {
        return this.requestId;
    }

    public BigDecimal getRequestedVolume() {
        return this.requestedVolume;
    }

    public BigDecimal getRequestedOpenPrice() {
        return this.requestedOpenPrice;
    }

    public String getTakerName() {
        return this.takerName;
    }

    public String getTakerLogin() {
        return this.takerLogin;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getSide() {
        return this.side;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public String getTimeInForce() {
        return this.timeInForce;
    }

    public void setSubmitted(final boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isSubmitted() {
        return submitted;
    }
}
