package com.tpam.service.management.service;

import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ManagementServiceTest {

    private static final String TAKER_NAME = "dummyTaker";
    private static final String TAKER_LOGIN = "dummyLogin";
    private static final String SYMBOL = "symbol";

    @Mock
    private TradeServiceClient tradeServiceClient;
    @InjectMocks
    private ManagementService managementService;

    @Test
    public void calculateTradeRequestsForClosingPositionsTest() {

        final BigDecimal maxInstrumentsPerSingleTrade = BigDecimal.valueOf(30.4);
        final BigDecimal symbolExposure = BigDecimal.valueOf(102.17);
        final StrategySymbolExposure exposure = new StrategySymbolExposure(symbolExposure, BigDecimal.ZERO, SYMBOL, TAKER_NAME, TAKER_LOGIN);

        given(tradeServiceClient.getStrategySymbolExposures(anyString(), anyString(), anyString())).willReturn(exposure);

        final List<SubmitTradeRequest> tradeRequests = managementService.calculateTradeRequestsForClosingPositions(TAKER_NAME, TAKER_LOGIN, SYMBOL, maxInstrumentsPerSingleTrade);
        assertThat(tradeRequests).hasSize(4);
        assertThat(tradeRequests.get(0).getRequestedVolume()).isEqualTo(maxInstrumentsPerSingleTrade);
        assertThat(tradeRequests.get(1).getRequestedVolume()).isEqualTo(maxInstrumentsPerSingleTrade);
        assertThat(tradeRequests.get(2).getRequestedVolume()).isEqualTo(maxInstrumentsPerSingleTrade);
        assertThat(tradeRequests.get(3).getRequestedVolume()).isEqualTo(BigDecimal.valueOf(10.97));
    }
}