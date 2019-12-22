package com.tpam.service.management.client.trade.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents total exposure for a symbol/takerName/takerLogin account triplet
 */
public class StrategySymbolExposure {

    private final BigDecimal totalBaseCurrencyExposure;
    private final BigDecimal totalQuoteCurrencyExposure;
    private final String symbol;
    private final String takerName;
    private final String takerLogin;

    public StrategySymbolExposure(final BigDecimal totalBaseCurrencyExposure,
                                  final BigDecimal totalQuoteCurrencyExposure,
                                  final String symbol,
                                  final String takerName,
                                  final String takerLogin) {
        this.totalBaseCurrencyExposure = totalBaseCurrencyExposure;
        this.totalQuoteCurrencyExposure = totalQuoteCurrencyExposure;
        this.symbol = symbol;
        this.takerName = takerName;
        this.takerLogin = takerLogin;
    }

    public BigDecimal getTotalBaseCurrencyExposure() {
        return totalBaseCurrencyExposure;
    }

    public BigDecimal getTotalQuoteCurrencyExposure() {
        return totalQuoteCurrencyExposure;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTakerName() {
        return takerName;
    }

    public String getTakerLogin() {
        return takerLogin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("totalBaseCurrencyExposure", totalBaseCurrencyExposure)
            .append("totalQuoteCurrencyExposure", totalQuoteCurrencyExposure)
            .append("symbol", symbol)
            .append("takerName", takerName)
            .append("takerLogin", takerLogin)
            .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final StrategySymbolExposure that = (StrategySymbolExposure) o;

        return new EqualsBuilder()
            .append(totalBaseCurrencyExposure, that.totalBaseCurrencyExposure)
            .append(totalQuoteCurrencyExposure, that.totalQuoteCurrencyExposure)
            .append(symbol, that.symbol)
            .append(takerName, that.takerName)
            .append(takerLogin, that.takerLogin)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(totalBaseCurrencyExposure)
            .append(totalQuoteCurrencyExposure)
            .append(symbol)
            .append(takerName)
            .append(takerLogin)
            .toHashCode();
    }
}
