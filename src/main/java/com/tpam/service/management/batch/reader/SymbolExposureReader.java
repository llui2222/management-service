package com.tpam.service.management.batch.reader;

import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import com.tpam.service.management.service.ManagementService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;

@StepScope
@Component
public class SymbolExposureReader implements ItemReader<SubmitTradeRequest>, InitializingBean {

    private final ManagementService managementService;
    private final String takerName;
    private final String takerLogin;
    private final String symbol;
    private final BigDecimal maxInstrumentsPerSingleTrade;

    private Iterator<SubmitTradeRequest> iterator;

    public SymbolExposureReader(final ManagementService managementService,
                                @Value("#{jobParameters['takerName']}") final String takerName,
                                @Value("#{jobParameters['takerLogin']}") final String takerLogin,
                                @Value("#{jobParameters['symbol']}") final String symbol,
                                @Value("#{jobParameters['maxInstrumentsPerSingleTrade']}") final BigDecimal maxInstrumentsPerSingleTrade) {
        this.managementService = managementService;
        this.takerName = takerName;
        this.takerLogin = takerLogin;
        this.symbol = symbol;
        this.maxInstrumentsPerSingleTrade = maxInstrumentsPerSingleTrade;
    }

    @Override
    public SubmitTradeRequest read() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        iterator = managementService.calculateTradeRequestsForClosingPositions(takerName, takerLogin, symbol, maxInstrumentsPerSingleTrade).iterator();
    }
}
