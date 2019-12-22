package com.tpam.service.management.batch.job;

import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import com.tpam.service.management.service.ManagementService;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ClosePositionsJobTest extends ClosePositionsJobBaseTest {

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;
    @MockBean
    private TradeServiceClient tradeServiceClient;

    @MockBean
    private ManagementService managementService;

    @Before
    public void setup() {
        final List<SubmitTradeRequest> requests = Arrays.asList(Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class));
        final StrategySymbolExposure zeroExposure = new StrategySymbolExposure(BigDecimal.ZERO, BigDecimal.ZERO, SYMBOL, TAKER_NAME, TAKER_LOGIN);

        given(managementService.calculateTradeRequestsForClosingPositions(TAKER_NAME, TAKER_LOGIN, SYMBOL, MAX_INSTRUMENTS_PER_SINGLE_TRADE)).willReturn(requests);
        given(tradeServiceClient.getStrategySymbolExposures(TAKER_NAME, TAKER_LOGIN, SYMBOL)).willReturn(zeroExposure);
    }

    @Override
    void verifyJobExecution(final JobExecution jobExecution) {
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions()).hasSize(1);
    }
}