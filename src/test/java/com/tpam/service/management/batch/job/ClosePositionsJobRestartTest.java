package com.tpam.service.management.batch.job;

import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import com.tpam.service.management.service.ManagementService;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class ClosePositionsJobRestartTest extends ClosePositionsJobBaseTest {

    @MockBean
    private TradeServiceClient tradeServiceClient;
    @MockBean
    private ManagementService managementService;

    private static final BigDecimal SYMBOL_EXPOSURE = BigDecimal.valueOf(102.17);

    @Before
    public void setup() {
        final List<SubmitTradeRequest> requests = Arrays.asList(Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class));

        final StrategySymbolExposure exposure = new StrategySymbolExposure(SYMBOL_EXPOSURE, BigDecimal.ZERO, SYMBOL, TAKER_NAME, TAKER_LOGIN);
        final StrategySymbolExposure zeroExposure = new StrategySymbolExposure(BigDecimal.ZERO, BigDecimal.ZERO, SYMBOL, TAKER_NAME, TAKER_LOGIN);

        given(managementService.calculateTradeRequestsForClosingPositions(TAKER_NAME, TAKER_LOGIN, SYMBOL, MAX_INSTRUMENTS_PER_SINGLE_TRADE)).willReturn(requests);
        given(tradeServiceClient.getStrategySymbolExposures(anyString(), anyString(), anyString()))
            .willReturn(exposure)
            .willReturn(zeroExposure);
    }

    @Override
    void verifyJobExecution(final JobExecution jobExecution) {
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions()).hasSize(2);
    }
}